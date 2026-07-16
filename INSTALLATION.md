# Installation Guide

## Prerequisites

- Paper Server 1.21 or higher
- Java 17 or higher
- Maven (for building from source)

## Steps

### 1. Download

Download the latest `vTarla-1.0.0.jar` from the releases page.

### 2. Installation

1. Place the JAR file in your server's `plugins` folder
2. Restart your server

### 3. Configuration

After restart, a configuration file will be created at `plugins/vTarla/config.yml`.

Edit this file to customize:
- Farm world name
- Coin settings
- Multiplier tiers
- Market items
- Messages and displays

### 4. Reload

Reload the plugin with:

```
/tarla reload
```

## Building from Source

### 1. Clone Repository

```bash
git clone https://github.com/Vixians/vTarla.git
cd vTarla
```

### 2. Build

```bash
mvn clean package
```

### 3. Install

The compiled JAR will be in `target/vTarla-1.0.0.jar`

Copy it to your server's `plugins` folder.

## Troubleshooting

### Plugin not loading

1. Check server console for errors
2. Verify Java version is 17+
3. Check `plugins/vTarla/logs/vTarla.log`

### Database errors

1. Delete `plugins/vTarla/database.db`
2. Reload the plugin
3. Database will be recreated

### Commands not working

1. Check player permissions
2. Verify plugin is enabled with `/plugins`
3. Check console for errors

## Support

For issues or questions:
- Open an issue on GitHub
- Check the documentation
- Review the configuration examples
