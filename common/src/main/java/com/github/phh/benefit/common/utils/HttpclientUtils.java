package com.github.phh.benefit.common.utils;

import io.vavr.control.Try;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: spring
 * @package com.github.phh.benefit.common.utils
 * @date 2019/4/16
 */
public class HttpclientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpclientUtils.class);
    private static PoolingHttpClientConnectionManager CONNMGR_WITHOUT_CERT;
    private static HttpClient CLIENT_WITHOUT_CERT;

    static {
        final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        CONNMGR_WITHOUT_CERT = new PoolingHttpClientConnectionManager(sfr);
        CONNMGR_WITHOUT_CERT.setDefaultMaxPerRoute(100);
        CONNMGR_WITHOUT_CERT.setMaxTotal(200);
        CONNMGR_WITHOUT_CERT.setValidateAfterInactivity(1000);
        CLIENT_WITHOUT_CERT = HttpClientBuilder.create()
                .setConnectionManager(CONNMGR_WITHOUT_CERT)
                .build();
    }

    /**
     * json请求
     *
     * @param url
     * @param jsonParam json请求参数
     * @param headers   请求头，可为null
     */
    public static Try<String> postJson(String url, String jsonParam, Header[] headers) {
        Request request = Request.Post(url);
        if (jsonParam != null) {
            request.bodyString(jsonParam, ContentType.APPLICATION_JSON);
        }
        if (headers != null && headers.length > 0) {
            request.setHeaders(headers);
        }
        return Try.of(() -> Executor.newInstance(CLIENT_WITHOUT_CERT)
                .execute(request)
                .returnContent()
                .asString(Consts.UTF_8));
    }

    /**
     * 表单请求
     *
     * @param url
     * @param params
     * @param headers 请求头，可为null
     * @param charset if null default Consts.UTF_8
     */
    public static Try<String> postForm(String url, List<NameValuePair> params, Header[] headers, Charset charset) {
        Request request = Request.Post(url);
        if (params != null) {
            request.bodyForm(params, charset != null ? charset : Consts.UTF_8);
        }
        if (headers != null && headers.length > 0) {
            request.setHeaders(headers);
        }
        return Try.of(() -> Executor.newInstance(CLIENT_WITHOUT_CERT)
                .execute(request)
                .returnContent()
                .asString(charset != null ? charset : Consts.UTF_8));
    }

    /**
     * post请求
     *
     * @param url
     * @param xml     xml请求参数
     * @param headers 请求头，可为null
     * @return String or HttpException
     */
    public static Try<String> postXml(String url, String xml, Header[] headers) {
        Request request = Request.Post(url);
        if (xml != null) {
            request.bodyString(xml, ContentType.create("text/xml", Consts.UTF_8));
        }
        if (headers != null && headers.length > 0) {
            request.setHeaders(headers);
        }
        return Try.of(() -> Executor.newInstance(CLIENT_WITHOUT_CERT)
                .execute(request)
                .returnContent()
                .asString(Consts.UTF_8));
    }


    /**
     * xml请求(post)
     *
     * @param url     网关
     * @param xml     请求xml参数
     * @param headers 请求头
     * @param charset 编码:java.nio.charset.Charset
     * @param timeout 超时时间:单位毫秒
     */
    public static Try<String> postXml(String url, String xml, Header[] headers, Charset charset, Integer timeout) {
        Request request = Request.Post(url);
        if (xml != null) {
            request.bodyString(xml, ContentType.create("text/xml", charset != null ? charset : Consts.UTF_8));
        }
        if (headers != null && headers.length > 0) {
            request.setHeaders(headers);
        }
        if (timeout != null) {
            request.connectTimeout(timeout);
            request.socketTimeout(timeout);
        }
        return Try.of(() -> Executor.newInstance(CLIENT_WITHOUT_CERT)
                .execute(request)
                .returnContent()
                .asString(charset != null ? charset : Consts.UTF_8));
    }


    /**
     * get请求
     *
     * @param url
     * @param params
     * @param headers 请求头，可为null
     * @return String or HttpException
     */
    public static Try<String> get(String url, List<NameValuePair> params, Header[] headers) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null && params.size() > 0) {
            uriBuilder.addParameters(params);
        }
        Request request = Request.Get(uriBuilder.build());
        if (headers != null && headers.length > 0) {
            request.setHeaders(headers);
        }
        return Try.of(() -> Executor.newInstance(CLIENT_WITHOUT_CERT)
                .execute(request)
                .returnContent()
                .asString(Consts.UTF_8));
    }


    /**
     * map转NameValuePair
     *
     * @param param
     * @return
     */
    public static List<NameValuePair> map2ValuePair(Map<String, String> param) {
        List<NameValuePair> params = new ArrayList<>();
        Set<Map.Entry<String, String>> set = param.entrySet();
        set.forEach(e -> {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
        });
        return params;
    }


}
