# Build
custom_build(
    # Name of the container image
    ref = 'catalog-service',
    # Command to build the container image
    command = './mvnw clean spring-boot:build-image -DskipTests -Dimage.name=$EXPECTED_REF',
    # Files to watch for changes to trigger a rebuild
    deps = ['pom.xml', 'src/**/*']
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])
