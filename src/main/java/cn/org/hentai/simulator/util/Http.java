package cn.org.hentai.simulator.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * Created by matrixy when 2019/8/22.
 */
public class Http
{
    public static Header[] h(String...args)
    {
        if (args.length % 2 != 0) throw new RuntimeException("invalid arguments length: " + args.length);
        Header[] headers = new Header[args.length / 2];
        for (int i = 0, k = 0; i < args.length; i+=2)
        {
            headers[k++] = new Header(args[i], args[i + 1]);
        }
        return headers;
    }

    public static String get(String url)
    {
        return get(url, null, "GBK");
    }

    public static String get(String url, Header[] headers, String encoding)
    {
        HttpURLConnection conn = null;
        InputStream reader = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(40960);
        try
        {
            conn = (HttpURLConnection) new java.net.URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            for (int i = 0; headers != null && i < headers.length; i++)
            {
                Header h = headers[i];
                conn.setRequestProperty(h.name, h.value);
            }
            int status = conn.getResponseCode();
            if (status == 301 || status == 302)
            {
                url = conn.getHeaderField("Location");
                return get(url, headers, encoding);
            }
            if (status != 200) throw new RuntimeException("HTTP: " + status + ", " + conn.getResponseMessage());

            if (encoding == null)
            {
                // Content-Type: text/html;charset=UTF-8
                String contentType = conn.getHeaderField("Content-Type");
                int idx = contentType.indexOf("charset=");
                if (idx > -1)
                {
                    encoding = contentType.substring(idx + 8);
                    if ((idx = encoding.indexOf(' ')) > -1) encoding = encoding.substring(0, idx);
                    if ((idx = encoding.indexOf(';')) > -1) encoding = encoding.substring(0, idx);
                }
                else encoding = "UTF-8";
            }

            boolean compressed = "gzip".equals(conn.getHeaderField("Content-Encoding"));

            int len = -1;
            byte[] block = new byte[512];

            if (compressed) reader = new GZIPInputStream(conn.getInputStream());
            else reader = conn.getInputStream();

            while ((len = reader.read(block)) > -1)
            {
                baos.write(block, 0, len);
            }
            return baos.toString(encoding);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Response _get(String url, Header[] headers, String encoding)
    {
        HttpURLConnection conn = null;
        InputStream reader = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(40960);
        Response resp = new Response(url);
        try
        {
            conn = (HttpURLConnection) new java.net.URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            for (int i = 0; headers != null && i < headers.length; i++)
            {
                Header h = headers[i];
                conn.setRequestProperty(h.name, h.value);
            }
            int status = conn.getResponseCode();
            resp.status = status;
            if (status == 301 || status == 302)
            {
                url = conn.getHeaderField("Location");
                System.err.println("redirect:" + url);
                return _get(url, headers, encoding);
            }
            if (status != 200) return resp;

            if (encoding == null)
            {
                // Content-Type: text/html;charset=UTF-8
                String contentType = conn.getHeaderField("Content-Type");
                int idx = contentType.indexOf("charset=");
                if (idx > -1)
                {
                    encoding = contentType.substring(idx + 8);
                    if ((idx = encoding.indexOf(' ')) > -1) encoding = encoding.substring(0, idx);
                    if ((idx = encoding.indexOf(';')) > -1) encoding = encoding.substring(0, idx);
                }
                else encoding = "UTF-8";
            }
            resp.encoding = encoding;

            resp.contentType = conn.getHeaderField("Content-Type");
            resp.cacheSeconds = getCacheSeconds(conn);
            boolean compressed = "gzip".equals(conn.getHeaderField("Content-Encoding"));

            int len = -1;
            byte[] block = new byte[512];

            if (compressed) reader = new GZIPInputStream(conn.getInputStream());
            else reader = conn.getInputStream();

            while ((len = reader.read(block)) > -1)
            {
                baos.write(block, 0, len);
            }
            resp.data = baos.toByteArray();
            return resp;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static class Response
    {
        public String url;
        public String encoding;
        public String contentType;
        public int status;
        public long cacheSeconds;
        public byte[] data;

        public Response(String url)
        {
            this.url = url;
        }

        public String getHTMLString()
        {
            try
            {
                return new String(data, encoding);
            }
            catch(Exception e)
            {
                return null;
            }
        }
    }

    public static long getCacheSeconds(HttpURLConnection conn)
    {
        String cacheControl = conn.getHeaderField("Cache-Control");
        if (cacheControl != null)
        {
            if (cacheControl.indexOf("no-cache") > -1) return -1;
            if (cacheControl.indexOf("max-age") > -1)
            {
                return Long.parseLong(cacheControl.replaceAll("\\D+", ""));
            }
        }

        String expires = conn.getHeaderField("Expires");
        if (expires != null)
        {
            return (new Date(expires).getTime() - System.currentTimeMillis()) / 1000;
        }

        return 0;
    }

    public static class Header
    {
        public String name;
        public String value;

        public Header(String name, String value)
        {
            this.name = name;
            this.value = value;
        }
    }
}
