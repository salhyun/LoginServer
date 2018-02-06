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
            int nMessageKind=0;
            String messagebody="";
            len=inStream.read(buf, len, buf.length);

            //String aa = MySocketMessage.getMessageBodyString(buf);

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
                if(nMessageType == MySocketMessage.MESSAGETYPE_REQUEST)
                {
                    nMessageKind = MySocketMessage.getMessageKind(buf);
                    if(nMessageKind == MySocketMessage.MESSAGEKIND_ECHO)
                    {
                        messagebody = MySocketMessage.getMessageBodyString(buf);

                        byte []respEcho = MySocketMessage.addMessageHeader(messagebody, MySocketMessage.MESSAGETYPE_RESPONSE, MySocketMessage.MESSAGEKIND_ECHO);
                        outStream.write(respEcho, 0, respEcho.length);

                        System.out.println("Response echo message = " + messagebody);
                    }
                    else if(nMessageKind == MySocketMessage.MESSAGEKIND_LOGIN)
                    {
                        //해당 계정
                        DBManager dbManager = new DBManager("salhyun", "333333333");

                        messagebody = MySocketMessage.getMessageBodyString(buf);

                        AccountInfoAdapter accountInfoAdapter = new AccountInfoAdapter();
                        dbManager.querySearch("account", "email", messagebody, accountInfoAdapter);

                        byte[] respBuf=null;
                        if(accountInfoAdapter.accountInfos.isEmpty())
                        {
                            //respBuf = MySocketMessage.addMessageHeader("there is no account", MySocketMessage.MESSAGETYPE_RESPONSE, MySocketMessage.MESSAGEKIND_LOGIN);

                            //이메일에서 '@' 앞에를 name으로 사용한다.
                            int at = messagebody.indexOf("@");
                            String name = messagebody.substring(0, at);
                            accountInfoAdapter.accountInfos.add(new AccountInfo(name, messagebody, 1, 100));
                            if(dbManager.queryInsert("account", accountInfoAdapter) == 1)
                            {
                                respBuf = MySocketMessage.addMessageHeader("welcome " + name, MySocketMessage.MESSAGETYPE_RESPONSE, MySocketMessage.MESSAGEKIND_LOGIN);
                            }
                            else
                            {
                                System.out.println("데이타베이스 서버에 접속할 수 없습니다.");
                            }
                        }
                        else
                        {
                            respBuf = MySocketMessage.addMessageHeader("login success", MySocketMessage.MESSAGETYPE_RESPONSE, MySocketMessage.MESSAGEKIND_LOGIN);

                            AccountManager.getInstance().addConnectedAccount(accountInfoAdapter.accountInfos.get(0));
                        }

                        outStream.write(respBuf, 0, respBuf.length);

                        System.out.println("Response Login = " + MySocketMessage.getMessageBodyString(respBuf));

                        dbManager.disconnectDriver();
                    }
                    else if(nMessageKind == MySocketMessage.MESSAGEKIND_LOBBYADDRESS)
                    {
                        byte[] respBuf=null;

                        //요청한 사용자 접속상태인지 확인 후 lobby address 전송
                        messagebody = MySocketMessage.getMessageBodyString(buf);
                        if(AccountManager.getInstance().findAccount(messagebody))
                        {
                            String lobbyAddr = "127.0.0.1@9100@";//ip address @ port @
                            respBuf = MySocketMessage.addMessageHeader(lobbyAddr, MySocketMessage.MESSAGETYPE_RESPONSE, MySocketMessage.MESSAGEKIND_LOBBYADDRESS);
                            outStream.write(respBuf, 0, respBuf.length);
                        }
                        else
                        {
                            System.out.println("접속되지 않는 사용자가 요청하였습니다. " + messagebody);
                        }
                    }
                }

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
