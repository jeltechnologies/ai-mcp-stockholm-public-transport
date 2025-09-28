FROM python:3.11-slim

LABEL org.opencontainers.image.title="mcpo"
LABEL org.opencontainers.image.description="Docker image for mcpo (Model Context Protocol OpenAPI Proxy)"
LABEL org.opencontainers.image.source="https://github.com/alephpiece/mcpo-docker"
LABEL org.opencontainers.image.licenses="MIT"

# install npx
RUN apt-get update && apt-get install -y --no-install-recommends \
	wget \
    curl \
    nodejs \
    npm \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# install uv
RUN curl -LsSf https://astral.sh/uv/install.sh | env UV_INSTALL_DIR="/usr/local/bin" sh

WORKDIR /app
EXPOSE 8000

#ENTRYPOINT ["uvx", "mcpo"]
#CMD ["--config", "/app/config.json"]
