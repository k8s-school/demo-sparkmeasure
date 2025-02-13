FROM docker.io/library/spark:3.4.1

USER root
RUN pip install sparkmeasure==0.24.0

ADD rootfs /

ARG spark_uid=185
ENV spark_uid ${spark_uid}
USER ${spark_uid}

# Exposer le port Spark
EXPOSE 4040

# Définir le point d'entrée
CMD ["/bin/bash"]
