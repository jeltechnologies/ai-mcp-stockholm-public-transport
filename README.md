# Ask ChatGTP "What is the next bus from Erikslund to Kungsbroplan?"
![slbus](https://github.com/user-attachments/assets/31bac689-3e99-4fc9-bab9-74e4d3bab13e)

<sup>*Image by by AleWi - Own work, CC BY-SA 4.0, https://commons.wikimedia.org/w/index.php?curid=131932539*</sup>

## Get the answer is in the chat, in real time! 
![chatting](https://github.com/user-attachments/assets/0bc9a190-0b1a-4e2e-adba-00db1e2ff092)

# About this tool
The tool provides AI agents access to real. time Stockholms public transport data. 
The tool gets it data from SL, the Stockholm public transport company. 

Functions:
- Translation of the rich data provided by SL APIs, to a human-like format that AI chatbots understand.
- Exposure of the API as Model Context Protocol (MCP) server.
- OpenAI support for Open WebUI, with the preconfigured MCPO proxy from Open WebU (https://github.com/open-webui/mcpo)

# Why this tool
Model Context Protocol (MCP) is the most important enablers of agentic AI. People call it the USB-C port for AI applications. Using MCP AI applications like Claude or ChatGPT can use it enabling them to access key information and perform tasks. 
I wrote this tool to learn how the Model Context Protocol works to understands it posisiblities and limitations.
## What did I learn so far
It is very easy to get started with MCP in modern tooling like the Spring Framework. It basically works out of the box. The fundamental difficulty with this kind of tools is the non-determenistic behavour of the chatbot. 
### AI acts like a cat
In a traditional API, the client will always perform the same requests and use the responses in the same way. It acts binary, like a dogs to their master. Chat bot clients however, act have more a cats. They do as they please. First of all you need to pursuede them to use the tool with crisp descriptions. You also never never really sure how they use the responses you provide. There is random behavior. This will make developing and especially maintaining this tool challenging. I see many simularaties with user experience in software engineering. 




# Getting started
The tool is deployed on your self-hosted server, for example in the cloud or on a homeserver. 
All you need a Linux box with Docker.

## Installation instructions
- Get a Linux box with Docker installed.
- Clone this repository
- Edit file file `config.json` in directory `docker/mcpo-config`. Change the IP address to the IP address of your Linux box.  
```
{
  "mcpServers": {
	"stockholm-public-transport": {
      "type": "sse",
      "url": "http://<your-ip-here>:18107/api/v1/sse"
    }
  }
}
```
- Start the containers by executing the command `docker compose up`
- This will start a Spring application in Java as well as MCPO. MCPO is a proxy from MCP to OpenAI API.
- Keep the docker running in the background to observe the logs.
- Optionally, you can also start the container with
  - `docker compose up -d` and then
  - `docker compose logs -f`

### Configuring the chatbot
MCP is an open standard, the tool thefore works with Open WebUI, and may also work with other AI tools like Claude or ChatGTP. 

#### Configuration of OpenWebUI 
This has been tested in Open WebUI v0.6.30.
- Open OpenWebUI as administrator, click on the account, choose Admin Panel, Settings, External Tools
- Click "Add Connection" 
- Use URL `http://<your-ip-here>:18108/stockholm-public-transport` and change the IP to the IP of the Linux box. 
- Choose a name and description. A good name and description way purseude the AI bot to use the tool.
![editconnectionopenwebui](https://github.com/user-attachments/assets/a76e47c4-96bf-481e-9b4a-bfc6eb6d9f27)
- Add the tool to models, so they are available in all chats.
![addtomodel](https://github.com/user-attachments/assets/06a8390a-4fe2-4f8e-af5c-2f8767ef1bab)










