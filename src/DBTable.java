import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-02-03.
 */
public class DBTable {
    private static final String mClassName="DBTable";

    protected static final int mISizeInBytes=Integer.SIZE/Byte.SIZE;

    public void stuff(ResultSet resultSet)
    {
    }

    protected void copyIntToByteArray(int src, ArrayList<Byte> dest)
    {
        byte[] a = convertDataType.IntToBytes(mISizeInBytes, convertDataType.BIG_EDIAN);
        for(int i=0; i<a.length; i++)
        {
            dest.add(a[i]);
        }

        a = convertDataType.IntToBytes(src, convertDataType.BIG_EDIAN);
        for(int i=0; i<a.length; i++)
        {
            dest.add(a[i]);
        }
    }
    protected void copyStringToByteArray(String src, ArrayList<Byte> dest)
    {
        byte[] length = convertDataType.IntToBytes(src.getBytes().length, convertDataType.BIG_EDIAN);
        for (int i=0; i<length.length; i++)
        {
            dest.add(length[i]);
        }
        byte[] buf = src.getBytes();
        for(int i=0; i<buf.length; i++)
        {
            dest.add(buf[i]);
        }
    }

}
