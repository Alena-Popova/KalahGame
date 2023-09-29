package sample.project.kalah.entity.usertype;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class IntegerArrayType implements UserType
{
    private static final int[] SQL_TYPES = {Types.ARRAY};

    @Override
    public int[] sqlTypes()
    {
        return SQL_TYPES;
    }

    @Override
    public Class<int[]> returnedClass()
    {
        return int[].class;
    }

    @Override
    public boolean equals(Object x, Object y)
    {
        if (x == y)
        {
            return true;
        }
        if (x == null || y == null)
        {
            return false;
        }
        return Arrays.equals((int[]) x, (int[]) y);
    }

    @Override
    public int hashCode(Object x)
    {
        return Arrays.hashCode((int[]) x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException
    {
        if (rs.wasNull())
        {
            return null;
        }

        Array array = rs.getArray(names[0]);
        if (array != null)
        {
            return array.getArray();
        }
        return new int[0];
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException
    {
        if (value == null)
        {
            st.setNull(index, SQL_TYPES[0]);
        }
        else
        {
            int[] castObject = (int[]) value;
            Integer[] integers = Arrays.stream(castObject).boxed().toArray(Integer[]::new);
            Connection connection = st.getConnection();
            st.setArray(index, connection.createArrayOf("integer", integers));
        }
    }

    @Override
    public Object deepCopy(Object value)
    {
        if (value == null) return null;

        int[] source = (int[]) value;
        int[] target = new int[source.length];
        System.arraycopy(source, 0, target, 0, source.length);
        return target;
    }

    @Override
    public boolean isMutable()
    {
        return true;
    }

    @Override
    public Serializable disassemble(Object value)
    {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
    {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
    {
        return deepCopy(original);
    }
}

