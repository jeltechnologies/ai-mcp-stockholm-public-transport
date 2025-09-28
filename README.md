# Let ChatGTP plan your next journey in Stockholm

![slbus](https://github.com/user-attachments/assets/57b41e62-1d7e-4e85-b015-645b8150ebcb)
(image by by AleWi - Own work, CC BY-SA 4.0, https://commons.wikimedia.org/w/index.php?curid=131932539)

## Why would I need this?
Enables to use AI chats with questions like "when is the next train from Sollentuna to Upplands Väsby?"

## How does it work?
This tool connects to SL, the Stockholm public transport company, and uses their APIs in real-time, providing a simplified interface for AI chatbots to access transport information. It acts as an Model Context Protocol (MCP) server, exposing the SL API's functionality.

## What do I need to get started?
The tool is targeted to people that self-host OpenWebUI and Ollama. Because MCP is an open standard, it may also work with other AI tools like Claude or even ChatGTP.

## Installation
For self-host solutions, such as homeservers:
- Get a Linux box with Docker installed.
- Clone the repository
- Edit file file config.json in directory docker/mcpo-config. Change the IP address to the IP address of your Linux box.
{
  "mcpServers": {
	"stockholm-public-transport": {
      "type": "sse",
      "url": "http://<your-ip-here>:18107/api/v1/sse"
    }
  }
}
- Start the containers with docker compose
- This will start a Spring application in Java as well as MCPO. MCPO is a proxy from MCP to OpenAI API.
- Open OpenWebUI as administrator, click on the account, choose Admin Panel, Settings, External Tools
- Connect using the settings as shown in the screenshot.

![editconnectionopenwebui](https://github.com/user-attachments/assets/a76e47c4-96bf-481e-9b4a-bfc6eb6d9f27)









