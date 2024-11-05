FROM ubuntu:22.04

RUN apt-get update && \
    apt-get install -y gcc g++ make && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    pip3 install -r requirements.txt && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 60000

CMD ["python3", "main.py"]
