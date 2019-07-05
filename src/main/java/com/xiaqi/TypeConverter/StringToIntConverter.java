package com.xiaqi.TypeConverter;

import com.xiaqi.annotation.MyService;

@MyService
public class StringToIntConverter implements TypeConverter<String, Integer> {

	@Override
	public Integer convert(String a) {
		return Integer.parseInt(a);
	}

}
