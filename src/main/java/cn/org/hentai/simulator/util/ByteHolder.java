package cn.org.hentai.simulator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by matrixy when 2018-06-15.
 */
public class ByteHolder
{
    static Logger logger = LoggerFactory.getLogger(ByteHolder.class);

    int offset = 0;
    int size = 0;               // 实际已经使用的大小
    int length = 0;             // 总的可用大小
    byte[] buffer = null;

    public ByteHolder(int bufferSize)
    {
        this.buffer = new byte[bufferSize];
        this.length = bufferSize;
    }

    public int length()
    {
        return this.length;
    }

    public int size()
    {
        return this.size;
    }

    public void write(byte[] data)
    {
        write(data, 0, data.length);
    }

    public void write(byte[] data, int offset, int length)
    {
        while (this.offset + length > buffer.length)
        {
            logger.error(String.format("exceed the max buffer size, max length: %d, data length: %d, resize to: %d", buffer.length, length, this.offset + length + 1024));
            byte[] temp = new byte[this.offset + length + 1024];
            System.arraycopy(buffer, 0, temp, 0, this.offset);
            buffer = temp;
        }

        // 复制一下内容
        System.arraycopy(data, offset, buffer, this.offset, length);

        this.offset += length;
        this.size += length;
    }

    public int writeInto(int offset, OutputStream dest, int bytes)
    {
        try
        {
            // 是否足够？
            if (offset >= this.size) return 0;

            int len = Math.min(bytes, size - offset);
            byte[] block = new byte[len];
            System.arraycopy(buffer, offset, block, 0, len);
            dest.write(block);

            return len;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public byte[] array()
    {
        return array(this.size);
    }

    public byte[] array(int length)
    {
        return Arrays.copyOf(this.buffer, length);
    }

    public void write(byte b)
    {
        this.buffer[offset++] = b;
        this.size += 1;
    }

    public void sliceInto(byte[] dest, int length)
    {
        System.arraycopy(this.buffer, 0, dest, 0, length);
        // 往前挪length个位
        System.arraycopy(this.buffer, length, this.buffer, 0, this.size - length);
        this.offset -= length;
        this.size -= length;
    }

    public void slice(int length)
    {
        // 往前挪length个位
        System.arraycopy(this.buffer, length, this.buffer, 0, this.size - length);
        for (int i = length, k = 0; i < this.size; i++, k++)
        {
            this.buffer[k] = this.buffer[i];
        }
        this.offset -= length;
        this.size -= length;
    }

    public byte get(int position)
    {
        return this.buffer[position];
    }

    public void clear()
    {
        this.offset = 0;
        this.size = 0;
    }

    public int getInt(int offset)
    {
        return ByteUtils.getInt(this.buffer, offset, 4);
    }

    public int getShort(int position)
    {
        int h = this.buffer[position] & 0xff;
        int l = this.buffer[position + 1] & 0xff;
        return ((h << 8) | l) & 0xffff;
    }
}