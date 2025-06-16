package edu.pnu.util;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomMyUtil {
	@SuppressWarnings("unchecked")
	public static String getUsernameFromOAuth2User(OAuth2User user) {
		Map<String, Object> attmap = user.getAttributes();
		String userString = (String)user.toString();
		String ret = "";
		
		if(userString.contains("www.googleapis.com")) {
			ret = "Google_" + attmap.get("name") + "_" + attmap.get("sub");
		}
		else if(userString.contains("response=")) {
			Map<String, Object> resmap = (Map<String, Object>)attmap.get("response");
			ret = "Naver_" + resmap.get("name") + "_" + resmap.get("id");
		}
		else if(userString.contains("http://t1.kakaocdn.net")) {
			Map<String, Object> propmap = 
					(Map<String, Object>)attmap.get("properties");
			ret = "Kakao_" + propmap.get("nickname") + "_" + attmap.get("id");
		}
		else if(userString.contains("api.github.com")) {
			ret = "Github_" + attmap.get("login") + "_" + attmap.get("id");
		}
		else {
			ret = "Facebook_" + attmap.get("name") + "_" + attmap.get("id");
		}
		
		ret = ret.replaceAll(",", "_").replaceAll(" ", "_");
		return ret;
	}
}