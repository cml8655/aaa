package cn.com.cml.pets.net;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import android.util.Log;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] data,
			ClientHttpRequestExecution exe) throws IOException {

		Log.d("TAG", "拦截器：" + request.getMethod() + ",data:" + new String(data)
				+ ",uri:" + request.getURI().toURL().toString());

		return exe.execute(request, data);

	}

}
