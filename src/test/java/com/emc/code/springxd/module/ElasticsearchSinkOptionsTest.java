package com.emc.code.springxd.module;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.springframework.xd.module.ModuleType.sink;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.xd.module.ModuleDefinition;
import org.springframework.xd.module.ModuleDefinitions;
import org.springframework.xd.module.options.DefaultModuleOptionsMetadataResolver;
import org.springframework.xd.module.options.ModuleOption;
import org.springframework.xd.module.options.ModuleOptionsMetadata;
import org.springframework.xd.module.options.ModuleOptionsMetadataResolver;

public class ElasticsearchSinkOptionsTest {

	@Test
	public void test() {
		ModuleOptionsMetadataResolver moduleOptionsMetadataResolver = new DefaultModuleOptionsMetadataResolver();
		String resource = "classpath:/";
		ModuleDefinition definition = ModuleDefinitions.simple("es-sink", sink, resource);
		ModuleOptionsMetadata metadata = moduleOptionsMetadataResolver.resolve(definition);		
		
		assertThat(metadata,containsInAnyOrder(moduleOptionNamed("index"), moduleOptionNamed("hosts"),
						moduleOptionNamed("type")));		
	}
	
	public static Matcher<ModuleOption> moduleOptionNamed(String name) {
		return hasProperty("name", equalTo(name));
	}	

}
