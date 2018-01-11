
public class MySocketMessage {

    public static final int MESSAGE_IDENTIFY=333;

	public static final int MESSAGETYPE_REQUEST=1;
    public static final int MESSAGETYPE_RESPONSE=2;

    public static final int MESSAGEKIND_ECHO=0;
    public static final int MESSAGEKIND_SEARCH=1;
    public static final int MESSAGEKIND_ACCOUNT=2;

    private static final int MESSAGETYPENUM=4;//identify + type + kind + size

    private static final int mIntSizeInByte=Integer.SIZE/Byte.SIZE;
    private static final int mHeaderSize=mIntSizeInByte*MESSAGETYPENUM;

    public static byte[] addMessageHeader(byte []message, int messageIdentify, int messageType, int messageKind)
    {
        int totalSize = mHeaderSize+message.length;
        byte []msgIdentify = convertDataType.IntToBytes(messageIdentify, convertDataType.BIG_EDIAN);
        byte []msgType = convertDataType.IntToBytes(messageType, convertDataType.BIG_EDIAN);
        byte []msgKind = convertDataType.IntToBytes(messageKind, convertDataType.BIG_EDIAN);
        byte []msgHSize = convertDataType.IntToBytes(totalSize, convertDataType.BIG_EDIAN);

        byte[] result = new byte[totalSize];

        int destPos=0;
        System.arraycopy(msgIdentify, 0, result, destPos, msgType.length);
        destPos += msgType.length;

        System.arraycopy(msgType, 0, result, destPos, msgType.length);
        destPos += msgType.length;

        System.arraycopy(msgKind, 0, result, destPos, msgKind.length);
        destPos += msgKind.length;

        System.arraycopy(msgHSize, 0, result, destPos, msgHSize.length);
        destPos += msgHSize.length;

        System.arraycopy(message, 0, result, destPos, message.length);

        return result;
    }
    public static int getMessageIdentify(byte []buf)
    {
        return convertDataType.BytesToInt(buf, 0, mIntSizeInByte, convertDataType.BIG_EDIAN);
    }
    public static int getMessageType(byte []buf)
    {
        return convertDataType.BytesToInt(buf, mIntSizeInByte, mIntSizeInByte, convertDataType.BIG_EDIAN);
    }
    public static int getMessageKind(byte []buf)
    {
        return convertDataType.BytesToInt(buf, mIntSizeInByte*2, mIntSizeInByte, convertDataType.BIG_EDIAN);
    }
    public static int getMessageSize(byte []buf)
    {
        return convertDataType.BytesToInt(buf, mIntSizeInByte*3, mIntSizeInByte, convertDataType.BIG_EDIAN);
    }
    public static byte[] getMessageBody(byte []buf)
    {
        int totalSize = getMessageSize(buf);
        int bodySize = totalSize-mHeaderSize;
        byte []result = new byte[bodySize];
        System.arraycopy(buf, mHeaderSize, result, 0, bodySize);
        return result;
    }
    public static String getMessageBodyString(byte []buf)
    {
        int totalSize = getMessageSize(buf);
        int bodySize = totalSize-mHeaderSize;
        byte []body = new byte[bodySize];
        System.arraycopy(buf, mHeaderSize, body, 0, bodySize);

        return convertDataType.ByteArrayToString(body, convertDataType.BIG_EDIAN);
    }

    public static int getHeaderSize()
    {
        return mHeaderSize;
    }
}
