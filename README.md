# 📱 Bank Notification Reader

Aplicación Android desarrollada en Kotlin que captura notificaciones push de aplicaciones bancarias y las envía a una API externa para su procesamiento y almacenamiento.

---

## 🎯 Descripción

Esta aplicación funciona como un **Notification Listener Service** que intercepta notificaciones de aplicaciones bancarias específicas en tiempo real y las reenvía a un endpoint HTTP para su registro y análisis.

---

## 🏦 Aplicaciones Bancarias Compatibles

| Banco/App | Package Name | País |
|-----------|-------------|------|
| **Yape** | `com.bcp.innovacxion.yapeapp` | 🇵🇪 Perú |
| **Mercado Pago** | `com.mercadopago.wallet` | 🇦🇷 Argentina |
| **Interbank** | `pe.com.interbank.mobilebanking` | 🇵🇪 Perú |
| **BCP** | `com.bcp.bank.bcp` | 🇵🇪 Perú |

---

## 🔧 Tecnologías Utilizadas

### Lenguaje y Framework
- **Kotlin** 1.8.10
- **Android SDK**
  - Compile SDK: 34
  - Target SDK: 34
  - Min SDK: 21 (Android 5.0 Lollipop)

### Dependencias
```gradle
androidx.appcompat:appcompat:1.6.1
androidx.core:core-ktx:1.10.1
com.google.android.material:material:1.9.0
```

### Herramientas de Desarrollo
- **Android Gradle Plugin**: 8.0.2
- **Java Version**: 1.8

---

## 📡 Endpoint API

### URL de Destino
```
http://demo.pexcreative.com/yapex/
```

### Parámetros enviados (GET)
| Parámetro | Descripción | Ejemplo |
|-----------|-------------|---------|
| `app` | Package name de la aplicación | `com.bcp.innovacxion.yapeapp` |
| `title` | Título de la notificación | `Confirmación de Pago` |
| `message` | Contenido completo de la notificación | `Pamela D. Valencia M. te envió un pago por S/ 1...` |

### Ejemplo de Request
```
http://demo.pexcreative.com/yapex/?app=com.bcp.innovacxion.yapeapp&title=Confirmaci%C3%B3n+de+Pago&message=Pamela+D.+Valencia+M.+te+envi%C3%B3+un+pago+por+S%2F+1
```

---

## 🔐 Permisos Requeridos

La aplicación requiere los siguientes permisos:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" />
```

### Configuración Manual del Usuario
El usuario debe activar manualmente el **acceso a notificaciones**:
1. Abrir la aplicación
2. Presionar "Dar Permisos"
3. Buscar "Yape Reader" o "Notification Reader"
4. Activar el toggle

---

## 📂 Estructura del Proyecto
```
NotificationReader/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/notificationreader/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── NotificationService.kt
│   │   │   ├── res/
│   │   │   │   └── layout/
│   │   │   │       └── activity_main.xml
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle
│   └── ...
└── README.md
```

---

## 🚀 Instalación y Uso

### Requisitos Previos
- Android Studio Arctic Fox o superior
- Dispositivo Android con versión 5.0+ (API 21+)
- Acceso a internet

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone [URL_DEL_REPOSITORIO]
cd NotificationReader
```

2. **Abrir en Android Studio**
   - File → Open
   - Seleccionar la carpeta del proyecto

3. **Sincronizar dependencias**
```bash
File → Sync Project with Gradle Files
```

4. **Compilar y ejecutar**
   - Conectar dispositivo Android (con depuración USB habilitada)
   - Click en el botón **Run** (▶️)

5. **Configurar permisos**
   - Abrir la app instalada
   - Presionar "Dar Permisos"
   - Activar "Acceso a notificaciones"

---

## 📊 Formato de Datos Capturados

### Ejemplo de CSV generado por la API
```csv
Timestamp,Fecha,Hora,App,Título,Mensaje
"2025-10-16 12:01:51",2025-10-16,12:01:51,com.bcp.innovacxion.yapeapp,"Confirmación de Pago","Pamela D. Valencia M. te envió un pago por S/ 1. El cód. de seguridad es: 593"
"2025-10-16 12:34:12",2025-10-16,12:34:12,com.mercadopago.wallet,"Recibiste $ 123,41","De Brian Hernan Ocaña desde su cuenta de Mercado Pago."
```

---

## 🔍 Logs y Debugging

### Ver logs en Android Studio (Logcat)

**Filtrar por notificaciones bancarias:**
```
Tag: BankNotification
```

**Filtrar por respuestas API:**
```
Tag: API
```

### Ejemplo de log exitoso:
```
D/BankNotification: com.bcp.innovacxion.yapeapp: Confirmación de Pago - Pamela D. Valencia M. te envió un pago...
D/API: OK 200
```

---

## ⚠️ Consideraciones de Seguridad

- Esta aplicación intercepta notificaciones sensibles con información financiera
- **Uso recomendado**: Entornos controlados y con consentimiento explícito
- Los datos se transmiten por HTTP sin cifrado (considerar HTTPS en producción)
- No almacena datos localmente en el dispositivo

---

## 🛠️ Troubleshooting

### La app se cierra al iniciar
- Verificar que se creó correctamente la carpeta `layout/`
- Limpiar y reconstruir: `Build → Clean Project → Rebuild Project`

### No se capturan notificaciones
- Verificar que el permiso "Acceso a notificaciones" está activado
- Reiniciar la app después de dar permisos
- Verificar que las apps bancarias están instaladas

### Error de conexión API
- Verificar conectividad a internet
- Comprobar que la URL de la API es accesible
- Revisar logs en Logcat con filtro `API`

---

## 📝 Notas de Desarrollo

### Configuración de Cleartext Traffic
El proyecto permite tráfico HTTP no cifrado mediante:
```xml
android:usesCleartextTraffic="true"
```

### Resolución de conflictos Kotlin
El proyecto fuerza versiones específicas de Kotlin stdlib para evitar conflictos:
```gradle
configurations.all {
    resolutionStrategy {
        force 'org.jetbrains.kotlin:kotlin-stdlib:1.8.10'
        force 'org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.10'
        force 'org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10'
    }
}
```

---

## 👨‍💻 Creador

**Pex Creative**  
Desarrollo de aplicaciones móviles y soluciones tecnológicas

- 🌐 Web: [https://pex.com.ar/desarrollo-apps/](https://pex.com.ar/desarrollo-apps/)
- 👤 Desarrollador: Ezequiel Del Vacchio
- 📱 WhatsApp: [+54 9 11 6920-0232](https://wa.me/5491169200232)
- 📧 Email: [soporte@pex.com.ar](mailto:soporte@pex.com.ar)

---

## 📄 Licencia

Este proyecto es de uso privado. Todos los derechos reservados © 2025 Pex Creative.

---

## 🔄 Historial de Versiones

### v1.0.0 (2025-10-16)
- ✅ Captura de notificaciones de Yape
- ✅ Captura de notificaciones de Mercado Pago
- ✅ Captura de notificaciones de Interbank
- ✅ Captura de notificaciones de BCP
- ✅ Envío automático a API externa
- ✅ Interfaz simple de activación de permisos

---

## 🤝 Contribuciones

Para consultas, mejoras o soporte técnico, contactar a través de los canales oficiales de Pex Creative.

---

**⚡ Hecho con Kotlin y ❤️ por Pex Creative**