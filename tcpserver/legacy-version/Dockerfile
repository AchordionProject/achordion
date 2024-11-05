FROM ubuntu:22.04 as builder

RUN apt-get update && apt-get install -y --no-install-recommends \
    bash \
    curl \
    wget \
    git \
    build-essential \
    ca-certificates

RUN wget -O cmake.tar.gz https://github.com/Kitware/CMake/releases/download/v3.30.1/cmake-3.30.1-linux-x86_64.tar.gz --no-check-certificate
RUN tar fvx /cmake.tar.gz
ENV PATH="/cmake-3.30.1-linux-x86_64/bin/:${PATH}"

WORKDIR /app

COPY . .

RUN cmake -Bbuild . && \
    cmake --build build -j16

FROM ubuntu:22.04

RUN apt-get update && apt-get install -y --no-install-recommends

COPY --from=builder /app/build/achordion_server /usr/bin/achordion_server

EXPOSE 60000

CMD ["achordion_server"]