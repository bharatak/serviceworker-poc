package org.bahmni;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceWorkerGenerator {
    public static void main(String[] args) throws IOException {
        if(args.length != 3){
            System.out.println(String.format("Please provide 3 arguments %s, %s, %s","bahmniapps location","bahmni_config location", "/Users/bharatak/main/dummy/src/main/resources/sw_js.template"));
        }
        ServiceWorkerGenerator serviceWorkerGenerator = new ServiceWorkerGenerator();

        Iterator iterator = FileUtils.iterateFiles(new File(args[0]),null,true);
        List<String> allFiles = new ArrayList<String>();

        while(iterator.hasNext()){
            File f = (File)iterator.next();
            serviceWorkerGenerator.getFilesFromBahmniDist(allFiles,args[0],f.getAbsolutePath());
        }

        iterator = FileUtils.iterateFiles(new File(args[1]),null,true);

        while(iterator.hasNext()){
            File f = (File)iterator.next();
            serviceWorkerGenerator.getFilesFromBahmniConfig(allFiles,args[1],f.getAbsolutePath());
        }

        String fileFromString = FileUtils.readFileToString(new File(args[2]));

        StringBuffer buffer = new StringBuffer();
        iterator = allFiles.iterator();
        while(iterator.hasNext()){
            buffer.append(iterator.next());
        }

        String finalString = fileFromString.replaceAll("replaceall", buffer.toString());
        System.out.println(finalString);

    }

    private void getFilesFromBahmniDist(List<String> allFiles,String replaceFrom,String prefixPath) {
        String stage1 = prefixPath.replaceAll(replaceFrom,"/bahmni/");
        allFiles.add(String.format("'%s',",stage1));
    }

    private void getFilesFromBahmniConfig(List<String> allFiles,String replaceFrom, String prefixPath){
        String stage1 = prefixPath.replaceAll(replaceFrom,"/bahmni_config/");
        allFiles.add(String.format("'%s',",stage1));

    }
}
