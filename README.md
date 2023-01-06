This is a Camel route that reads fight information from the [Quarkus Superheroes](https://github.com/quarkusio/quarkus-super-heroes) and posts them to the [MessageHub build in the Camel Workshop](https://github.com/RedHat-Middleware-Workshops/workshop-camel3).

Here's how to set this up:
1. The [Quarkus Superheroes](https://github.com/quarkusio/quarkus-superheroes) needs to be deployed.
2. The [MessageHub build in the Camel Workshop](https://github.com/RedHat-Middleware-Workshops/workshop-camel3) needs to be completed so that the Kafka instance for MessageHub is available.
3. The [`supes2messagehub-cm.yaml`](supes2messagehub-cm.yaml) needs to be deployed (you may need to tweak it's values depending on what namespace you're deploying to):
    ```bash
    oc apply -f supes2messagehub-cm.yaml
    ```

4. There are a few ways you can deploy the integration:
   1. By simply applying the [`Integration` Kubernetes custom resource](supes2messagehub-integration.yaml). This will deploy everything needed to run the integration in a single step.
       ```bash
       oc apply -f supes2messagehub-integration.yaml
       ```

   2. Using the [`kamel` CLI](https://camel.apache.org/camel-k/1.11.x/cli/cli.html) to manually run [the integration](supes2messagehub.yaml):
       ```bash
       kamel run --resource=configmap:supes2messagehub --dependency=camel:jslt --dependency=mvn:io.quarkus:quarkus-apicurio-registry-avro supes2messagehub.yaml
       ```

The rest of the code in this project is essentially the same, except it is a full camel-quarkus project with source code that could be built as a container image.