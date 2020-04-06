package com.ssm.api;

import java.io.*;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019-07-31 08:38
 */
public class Vtt2Srt
{

    public static void main(String[] args)
    {
        Vtt2Srt vtt2Srt = new Vtt2Srt();
        String dirName = "D:\\sources\\javaweb\\git\\ue4\\Kotlin for Android Beginner to Advanced\\2. Kotlin Foundation";
        File file = new File(dirName);
        try
        {
            vtt2Srt.dueFile(file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private void dueFile(File file) throws IOException
    {
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            assert files != null;
            for (File childFile : files)
            {
                dueFile(childFile);
            }
        } else
        {
            if (file.getName().contains(".vtt")) changeVtt2Srt(file);
        }
    }

    private void changeVtt2Srt(File file) throws IOException
    {
        String fileName = file.getAbsolutePath();
        String dstName = fileName.substring(0, fileName.length() - 4) + ".srt";


        BufferedReader reader = new BufferedReader(new FileReader(file));

        File dstFile = new File(dstName);
        dstFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(dstFile));

        String prevLine = "";
        String line;
        int n = 1;
        Boolean flag = true;
        while ((line = reader.readLine()) != null)
        {
            if (!line.contains("WEBVTT"))
            {
                if (!line.equals(""))
                {
                    if (flag)
                    {
                        writer.write(prevLine);
                        String aline = line.substring(0, "00:50,970 --> ".length());
                        String bline = line.substring("00:50,970 --> ".length());
                        writer.write(("00:" + aline + "00:" + bline).replace('.', ',')+"\n");
                        n++;
                        prevLine = n + "\n";
                        flag = false;
                    } else
                    {
                        writer.write(line.replace('.', ',')+"\n");
                    }

                } else
                {
                    flag = true;
                    writer.write(line.replace('.', ',')+"\n");
                }
            } else
            {
                prevLine = String.valueOf(n + '\n');
            }
        }

        reader.close();
        writer.flush();
        writer.close();
    }
}
