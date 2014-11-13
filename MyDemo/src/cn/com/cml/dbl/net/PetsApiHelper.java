package cn.com.cml.dbl.net;

import java.util.Map;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import cn.com.cml.dbl.model.RequestModel;

@Rest(converters = { GsonHttpMessageConverter.class,
		StringHttpMessageConverter.class }, interceptors = RequestInterceptor.class)
public interface PetsApiHelper extends RestClientErrorHandling,
		RestClientSupport {

	@Get("http://192.168.1.193:8080/Param/index.jsp?name=123456")
	String index();

	@Post("http://192.168.1.193:8080/Param/index.jsp")
	String indexWithParam(Map<String, String> param);

	@Post("http://192.168.1.193:8080/Param/index.jsp")
	String indexWithModel(RequestModel model);
}
