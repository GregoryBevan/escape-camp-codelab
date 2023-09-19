FROM gitpod/workspace-full

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 17.0.9-tem && \
    sdk default java 17.0.9-tem && \
    sdk install gradle 8.3 && \
    sdk default gradle 8.3"