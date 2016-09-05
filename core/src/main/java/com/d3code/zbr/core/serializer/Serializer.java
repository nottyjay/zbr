package com.d3code.zbr.core.serializer;

import java.io.IOException;

/**
 * Created by jileilei on 16/9/5.
 */
public interface Serializer {

    public String name();

    public byte[] serialize(Object obj) throws IOException;

    public Object deserialize(byte[] bytes) throws IOException;
}
