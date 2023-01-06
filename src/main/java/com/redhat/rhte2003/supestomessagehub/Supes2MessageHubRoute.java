// camel-k: language=java dependency=camel:jslt dependency=mvn:io.quarkus:quarkus-apicurio-registry-avro resource=configmap:supes2messagehub

package com.redhat.rhte2003.supestomessagehub;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Supes2MessageHubRoute extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		var superheroesBrokers = "?brokers={{superheroes.kafka.brokers}}";
		var superheroesGroupId = "&groupId={{superheroes.kafka.groupId}}";
		var superheroesValueDeserializer = "&valueDeserializer={{superheroes.kafka.valueDeserializer}}";
		var superheroesClientId = "&clientId={{superheroes.kafka.clientId}}";
		var superheroesRegistryUrl = "&additionalProperties.apicurio.registry.url={{superheroes.apicurio.registryurl}}";
		var superheroesRegistryDatumProvider = "&additionalProperties.apicurio.registry.avro-datum-provider={{superheroes.apicurio.datumprovider}}";

		var messageHubBrokers = "?brokers={{messagehub.kafka.brokers}}";
		var messageHubClientId = "&clientId={{messagehub.kafka.clientId}}";

		from("kafka:{{superheroes.kafka.topic.name}}" + superheroesBrokers + superheroesGroupId + superheroesClientId + superheroesValueDeserializer + superheroesRegistryUrl + superheroesRegistryDatumProvider)
			.convertBodyTo(String.class)
			.log("Raw fight: ${body}")
			.to("jslt:supes2messagehub/supes2messagehub.jslt?prettyPrint=true")
			.log("Fight result:\n${body}")
			.to("kafka:{{messagehub.kafka.topic.name}}" + messageHubBrokers + messageHubClientId);
	}
}
