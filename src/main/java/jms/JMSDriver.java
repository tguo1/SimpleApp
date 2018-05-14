package jms;

import javax.jms.*;
import javax.naming.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class JMSDriver {
    public static void main(String[] args) {
        try {

            Context ctx = new InitialContext();
            QueueConnectionFactory f = (QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");
            QueueConnection con = f.createQueueConnection();
            con.start();

            QueueSession ses = con.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            Queue t = (Queue)ctx.lookup("myQueue");
            QueueSender sender = ses.createSender(t);
            TextMessage msg = ses.createTextMessage();

            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Enter message ('End' to terminate): ");
                String s = b.readLine();

                if (s.equalsIgnoreCase("End")) break;

                msg.setText(s);
                sender.send(msg);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
