FROM docker-registry1.ocean.douyu.com/java-centos:1.8_162_b12

ADD ./demo-tpl-release/target/demo-tpl-release-@project.version@.tar.gz  /opt

RUN set -ex && \
    chown -R ocean:ocean /opt/demo-tpl && \
    chmod +x /opt/demo-tpl/bin/*

CMD ["/bin/bash","-l"]
