FROM docker.io/library/spark:3.4.1

USER root
RUN pip install sparkmeasure==0.24.0

# Setup for the Prometheus JMX exporter.
# Add the Prometheus JMX exporter Java agent jar for exposing metrics sent to the JmxSink to Prometheus.
ENV JMX_EXPORTER_AGENT_VERSION 1.1.0
ADD https://github.com/prometheus/jmx_exporter/releases/download/${JMX_EXPORTER_AGENT_VERSION}/jmx_prometheus_javaagent-${JMX_EXPORTER_AGENT_VERSION}.jar /opt/spark/jars
RUN chmod 644 /opt/spark/jars/jmx_prometheus_javaagent-${JMX_EXPORTER_AGENT_VERSION}.jar

ADD rootfs /

ARG spark_uid=185
ENV spark_uid ${spark_uid}
USER ${spark_uid}

ENV PYTHONPATH /opt/spark/python

# Exposer le port Spark
EXPOSE 4040

# Définir le point d'entrée
CMD ["/bin/bash"]
