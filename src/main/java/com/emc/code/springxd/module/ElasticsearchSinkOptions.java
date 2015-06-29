package com.emc.code.springxd.module;

import org.springframework.xd.module.options.spi.ModuleOption;

public class ElasticsearchSinkOptions {

	private String[] hosts;
	
	private String index;
	
	private String type;
	
	@ModuleOption(value="array of host names",defaultValue="localhost")
	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}
	
	@ModuleOption("index name")
	public void setIndex(String index) {
		this.index = index;
	}
	
	@ModuleOption("type name")
	public void setType(String type) {
		this.type = type;
	}

	public String[] getHosts() {
		return hosts;
	}

	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}	
	
	
}
