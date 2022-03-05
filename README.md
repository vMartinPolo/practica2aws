# awsPractica1

## Como crear un servicio en Ubuntu

- Posicionarse en : /etc/systemd/system/

```bash
cd /etc/systemd/system/
```

- Crear el archivo que contendra la información de nuestro servicio: 
```bash
sudo nano practica1.service
```
- Incluir la información del servicio: 

```bash
[Unit]
Description=Daemon Practice1 #Breve descripción
[Service]
User=ubuntu #Usuario que va a levantar el servivio
ExecStart=sh /home/ubuntu/runProduction.sh #ruta absoluta de donde se encuentra el script
[Install]
WantedBy=multi-user.target #directivas de uso
```

- Ahora debemos comprobar los permisos y en caso de que sea necesario dar permisos: 

```bash
ls -l #Para comprobar los permisos
chmod 777 practica1.service #Para dar permisos a todo el mundo
```

- Habilitar el servicio: 

```bash
sudo systemctl enable practica1.service
```

- Levantamos el servicio: 

```bash
sudo systemctl start practica1.service
```

- Y por último sólo nos queda comprobar que levanta bien:

 ```bash
sudo systemctl status practica1.service
```
