package com.example.plantio;
import android.content.Context;
import android.widget.Toast;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHClass {

    private final String host = "192.168.0.200";
    private final String user = "pi";
    private final String password = "";

    private static SSHClass sshClass = null;

    private SSHClass()
    {}

    public static SSHClass getInstance()
    {
        if(sshClass == null)
        {
            sshClass = new SSHClass();
        }
        return sshClass;
    }


    public void runCommand(String command)
    {

        JSch jsch = new JSch();
        Session session;
        try{

            jsch.setConfig("StrictHostKeyChecking", "no");

            //open a new session
            session = jsch.getSession(user, host, 22); // pi, 192.168.43.213, 22
            System.out.println("--------------------->" + session);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password); //passwrord : raspberry
            session.connect(1000); //connect session
            System.out.println("Connected");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

