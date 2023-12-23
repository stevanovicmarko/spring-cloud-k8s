# Build
custom_build(
    # Name of the container image
    ref = 'edge-service',
    # Command to build the container image
    command = 'cd edge-service && ./mvnw clean spring-boot:build-image -DskipTests -Dimage.name=$EXPECTED_REF',
    # Files to watch for changes to trigger a rebuild
    deps = ['edge-service/pom.xml', 'edge-service/src/**/*']
)

custom_build(
    # Name of the container image
    ref = 'config-service',
    # Command to build the container image
    command = 'cd config-service && ./mvnw clean spring-boot:build-image -DskipTests -Dimage.name=$EXPECTED_REF',
    # Files to watch for changes to trigger a rebuild
    deps = ['config-service/pom.xml', 'config-service/src/**/*']
)

custom_build(
    # Name of the container image
    ref = 'catalog-service',
    # Command to build the container image
    command = 'cd catalog-service && ./mvnw clean spring-boot:build-image -DskipTests -Dimage.name=$EXPECTED_REF',
    # Files to watch for changes to trigger a rebuild
    deps = ['./catalog-service/pom.xml', './catalog-service/src/**/*']
)

custom_build(
    # Name of the container image
    ref = 'order-service',
    # Command to build the container image
    command = 'cd order-service && ./mvnw clean spring-boot:build-image -DskipTests -Dimage.name=$EXPECTED_REF',
    # Files to watch for changes to trigger a rebuild
    deps = ['./order-service/pom.xml', './order-service/src/**/*']
)

# Deploy
k8s_yaml(['k8s/config-maps.yml', 'k8s/deployments.yml', 'k8s/services.yml'])

# Manage
k8s_resource('edge-service', port_forwards=['9000'])
k8s_resource('config-service', port_forwards=['8888'])
k8s_resource('catalog-service', port_forwards=['9001'])
k8s_resource('order-service', port_forwards=['9002'])
