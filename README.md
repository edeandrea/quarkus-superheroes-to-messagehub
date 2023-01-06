This is a Camel route that reads fight information from the [Quarkus Superheroes](https://github.com/quarkusio/quarkus-super-heroes) and posts them to the [MessageHub build in the Camel Workshop](https://github.com/RedHat-Middleware-Workshops/workshop-camel3).

First, the [`supes2messagehub-cm.yaml`](supes2messagehub-cm.yaml) needs to be deployed (you may need to tweak it's values depending on what namespace you're deploying to):
    ```bash
    oc apply -f supes2messagehub-cm.yaml
    ```

Then, there are a few ways you can deploy the integration:
1. By simply applying the `Integration` Kubernetes custom resource. This will deploy everything needed to run the integration in a single step.
    ```bash
    oc apply -f supes2messagehub-integration.yaml
    ```

3. Using the [`kamel` CLI](https://camel.apache.org/camel-k/1.11.x/cli/cli.html):
    ```bash
    kamel run --resource=configmap:supes2messagehub --dependency=camel:jslt --dependency=mvn:io.quarkus:quarkus-apicurio-registry-avro supes2messagehub.yaml
    ```