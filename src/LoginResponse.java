import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginResponse {
    public static final String mClassName="LoginResponse";

    public LoginResponse()
    {

    }
    public void accept(ServerSocket server, String charset)
    {

        try {
            Socket con = server.accept();

            BufferedInputStream inStream = new BufferedInputStream(con.getInputStream());
            BufferedOutputStream outStream = new BufferedOutputStream(con.getOutputStream());

            String str = "";
            byte buf[] = new byte[256];
            int len=0, nMessageSize=0;

            int nMessageType=0;
            String messagebody="";
            len=inStream.read(buf, len, buf.length);
            nMessageSize = MySocketMessage.getMessageSize(buf);
            while(len < nMessageSize)
            {
                len += inStream.read(buf, len, buf.length);

                //데이타 전송중 중지 되었을 경우 처리

                if(str.indexOf("\n") != -1)
                    break;
            }

            nMessageType = MySocketMessage.getMessageType(buf);
            messagebody = MySocketMessage.getMessageBodyString(buf);
            System.out.println("received message body=" + "\"" + messagebody + "\"");

            if(charset.equals("UTF-8"))
            {
                //messagebody += strResult;
                System.out.println("to send message=" + "\"" + messagebody + "\"");

                outStream.write(messagebody.getBytes(), 0, messagebody.getBytes().length);
            }
            else if(charset.equals("EUC-KR"))
            {
            }
            else
            {
            }

            outStream.flush();

            outStream.close();
            inStream.close();
            con.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
