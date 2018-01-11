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

            //유효성 검사
            int nIdentify = MySocketMessage.getMessageIdentify(buf);
            if(nIdentify != MySocketMessage.MESSAGE_IDENTIFY)//유효하지 않는 메시지
            {
                outStream.flush();
                outStream.close();
                inStream.close();
                con.close();
                return;
            }

            nMessageSize = MySocketMessage.getMessageSize(buf);
            while(len < nMessageSize)
            {
                len += inStream.read(buf, len, buf.length);

                //데이타 전송중 중지 되었을 경우 처리

                if(str.indexOf("\n") != -1)
                    break;
            }

            nMessageType = MySocketMessage.getMessageType(buf);

            if(charset.equals("UTF-8"))
            {
                System.out.println("to send message=" + "\"" + messagebody + "\"");

                if(nMessageType == MySocketMessage.MESSAGETYPE_REQUEST)
                {
                    if(MySocketMessage.getMessageKind(buf) == MySocketMessage.MESSAGEKIND_ECHO)
                    {
                        messagebody = MySocketMessage.getMessageBodyString(buf);

                        MySocketMessage mySocketMessage = new MySocketMessage();
                        byte []msgEcho = mySocketMessage.addMessageHeader(messagebody.getBytes(), MySocketMessage.MESSAGE_IDENTIFY, MySocketMessage.MESSAGETYPE_RESPONSE, MySocketMessage.MESSAGEKIND_ECHO);
                        outStream.write(msgEcho, 0, msgEcho.length);
                    }
                }

                //해당 계정
                DBManager DBManager = new DBManager("salhyun", "333333333");
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
