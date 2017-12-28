import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class convertDataType {
	public final static int BIG_EDIAN = 1;
    public final static int Little_EDIAN = 2;


    //============================String to byteArray====================================//
    public byte[] StringToBytes(String Value,int Order)
    {
        byte[] temp = Value.getBytes();
        temp = ChangeByteOrder(temp,Order);
        return temp;
    }
    //============================byteArray to String====================================//

    /**
     * @param Value 읽을 바이트 배열
     * @return String
     */
    public String ByteArrayToString(byte[] Value,int Order)
    {
        byte[] temp = Value;
        temp = ChangeByteOrder(temp,Order);
        return new String(temp);
    }

    /**
     * @param oridata 읽을 바이트 배열
     * @param start 읽을 바이트 배열 시작점
     * @param len 읽을 바이트 배열 길이
     * @return 스트링 문자열
     */
    public String BytesToString(byte[] oridata,int start,int len,int Order)
    {
        byte[] temp = new byte[len];
        System.arraycopy(oridata,start,temp,0,len);
        temp = ChangeByteOrder(temp,Order);
        return new String(temp);
    }

    //===========================Short to byteArray===================================//
    public byte[] ShortToBytes(short Value,int Order)
    {
        byte[] temp;
        temp = new byte[]{(byte)((Value & 0xFF00) >> 8),(byte)(Value & 0x00FF) };
        temp = ChangeByteOrder(temp,Order);
        return  temp;
    }

    //===========================byteArray to Short===================================//

    /**
     * @param Value short형으로 바뀔 바이트
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return
     */
    public short BytesToShort(byte[] Value,int Order)
    {
        short newValue = 0;
        byte[] temp = Value;

        temp = ChangeByteOrder(temp,Order);

        newValue |= (((short)temp[0])<<8)&0xFF00;
        newValue |= (((short)temp[1]))&0xFF;
        return newValue;
    }

    /**
     * @param oridata 읽을 바이트 배열
     * @param start 읽을 바이트의  시작점
     * @param len 읽을 바이트 배열 길이
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return short
     */
    public short BytesToShort(byte[] oridata,int start,int len,int Order)
    {
        short newValue = 0;
        byte[] temp = new byte[len];

        System.arraycopy(oridata,start,temp,0,len);

        temp = ChangeByteOrder(temp,Order);

        newValue |= (((short)temp[0])<<8)&0xFF00;
        newValue |= (((short)temp[1]))&0xFF;

        return newValue;
    }

    //============================Int to byteArray====================================//
    /**
     * @param Value 변환될 인트
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return 변환된 바이트 배열
     */
    public byte[] IntToBytes(int Value,int Order)
    {
        byte[] temp = new byte[4];
        ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE/8);
        buff.putInt(Value);
        temp = ChangeByteOrder(buff.array(),Order);
        return  temp;
    }

    //============================byteArray to Int====================================//
    /**
     * @param Value int로 변환할 바이트 배열
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return int
     */
    public int BytesToInt(byte[] Value,int Order)
    {
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff = ByteBuffer.wrap(Value);

        if(Order == BIG_EDIAN) buff.order(ByteOrder.BIG_ENDIAN);
        else if(Order == Little_EDIAN) buff.order(ByteOrder.LITTLE_ENDIAN);
        return  buff.getInt();
    }

    /**
     * @param oridata 읽을 바이트 배열
     * @param start 읽을 바이트의  시작점
     * @param len 읽을 바이트 배열 길이
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return int
     */
    public int BytesToInt(byte[] oridata,int start,int len,int Order)
    {
        byte[] temp = new byte[len];
        System.arraycopy(oridata,start,temp,0,len);
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff = ByteBuffer.wrap(temp);

        if(Order == BIG_EDIAN) buff.order(ByteOrder.BIG_ENDIAN);
        else if(Order == Little_EDIAN) buff.order(ByteOrder.LITTLE_ENDIAN);
        return  buff.getInt();
    }


    //============================Float to byteArray====================================//
    /**
     * @param value 바이트로 변경할 float형 Value
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return 바이트 어레이
     */
    public byte[] FloatTobytes(float value,int Order)
    {
        byte[] Temp = new byte[4];

        int intBits=Float.floatToIntBits(value);

        Temp[0]=(byte)((intBits&0x000000ff)>>0);
        Temp[1]=(byte)((intBits&0x0000ff00)>>8);
        Temp[2]=(byte)((intBits&0x00ff0000)>>16);
        Temp[3]=(byte)((intBits&0xff000000)>>24);

        Temp = ChangeByteOrder(Temp,Order);
        return Temp;
    }

    //============================byteArray to Float====================================//
    /**
     * @param Value float으로 변경 할 byteArray
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return float
     */
    public float BytesToFloat (byte[] Value,int Order)
    {
        int accum = 0;
        int i = 0;
        byte[] Temp = Value;

        Temp = ChangeByteOrder(Temp,Order);


        for (int shiftBy = 0; shiftBy < 32; shiftBy += 8 )
        {
            accum |= ( (long)( Temp[i] & 0xff ) ) << shiftBy;
            i++;
        }
        return Float.intBitsToFloat(accum);
    }

    /**
     * @param oridata 읽을 바이트 배열
     * @param start 읽을 바이트의  시작점
     * @param len 읽을 바이트 배열 길이
     * @param Order BIG_EDIAN,Little_EDIAN 변수에 적용되어 있음
     * @return float
     */
    public float BytesTofloat(byte[] oridata,int start,int len,int Order)
    {
        int accum = 0;
        int i = 0;
        byte[] Temp = new byte[len];
        System.arraycopy(oridata,start,Temp,0,len);

        Temp = ChangeByteOrder(Temp,Order);

        for (int shiftBy = 0; shiftBy < 32; shiftBy += 8 )
        {
            accum |= ( (long)( Temp[i] & 0xff ) ) << shiftBy;
            i++;
        }
        return Float.intBitsToFloat(accum);
    }


    //====================================== 위치 바꾸는 함수==================================//
    private byte[] ChangeByteOrder(byte[] value,int Order)
    {
        int idx = value.length;
        byte[]Temp = new byte[idx];

        if(Order == BIG_EDIAN)
        {
            Temp = value;
        }

        else if(Order ==Little_EDIAN)
        {
            for(int i = 0 ; i < idx ; i ++)
            {
                Temp[i] = value[idx - (i+1)];
            }
        }

        return Temp;
    }

}
