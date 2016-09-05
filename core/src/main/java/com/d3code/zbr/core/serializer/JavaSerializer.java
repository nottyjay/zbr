package com.d3code.zbr.core.serializer;

import com.d3code.zbr.core.expection.ZBRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by jileilei on 16/9/5.
 */
public class JavaSerializer implements Serializer {

    private static final Logger LOG = LoggerFactory.getLogger(JavaSerializer.class);

    @Override
    public String name() {
        return "java";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } finally {
            if(oos != null)
                try {
                    oos.close();
                } catch (IOException e) {}
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        if(bytes == null || bytes.length == 0)
            return null;
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new ZBRException(e);
        } finally {
            if(ois != null)
                try {
                    ois.close();
                } catch (IOException e) {}
        }
    }
}
