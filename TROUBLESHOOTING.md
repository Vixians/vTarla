# Troubleshooting Guide

## Common Issues

### Plugin fails to load

**Problem:** Plugin doesn't appear in `/plugins` list

**Solutions:**
1. Check server console for error messages
2. Verify Java version: `java -version` (must be 17+)
3. Ensure Paper is installed, not Spigot
4. Check file permissions on the JAR
5. Look for errors in `plugins/vTarla/logs/vTarla.log`

### Database corruption

**Problem:** "Database locked" or "Corrupt database" errors

**Solutions:**
1. Stop the server
2. Delete `plugins/vTarla/database.db`
3. Start the server (new database will be created)
4. All player data will be lost, so restore from backup if available

### Commands not working

**Problem:** Commands give "Unknown command" error

**Solutions:**
1. Verify permissions: Check player has `tarla.command` permission
2. Reload plugin: `/tarla reload`
3. Check console for command registration errors
4. Verify plugin.yml is not corrupted

### Players not earning coins

**Problem:** Players break crops but earn no coins

**Solutions:**
1. Check farm world is set correctly: `/tarla` (check console)
2. Verify player is in the correct world
3. Check crop type is in tracked crops list
4. Verify player has permission to earn coins
5. Check if coins-per-crop is set to 0 in config

### Market doesn't open

**Problem:** Inventory GUI doesn't appear when opening market

**Solutions:**
1. Check if market is enabled in config
2. Verify player has `tarla.market` permission
3. Check for errors in console
4. Ensure market items are configured

### Discount not applying

**Problem:** Discount set but items still cost full price

**Solutions:**
1. Verify discount is enabled in config
2. Check discount is > 0: `/taladiscount set 20`
3. Reopen market GUI after setting discount
4. Check max-discount isn't reached

## Performance Issues

### High TPS usage

**Problem:** Server TPS drops when using vTarla

**Solutions:**
1. Increase auto-save interval: `auto-save-interval: 600` (10 minutes)
2. Disable ActionBar/BossBar if not needed
3. Reduce update intervals
4. Check database file size - it shouldn't exceed 100MB
5. Verify async tasks are running: Check console on startup

### Database slow

**Problem:** Database queries taking too long

**Solutions:**
1. Backup and delete `database.db` to reset
2. Check disk space available
3. Verify no antivirus software is scanning the database
4. Increase `-Xmx` server memory if under 2GB allocated

## Data Loss

### Player coins disappeared

**Problem:** Player loses coins after restart

**Solutions:**
1. Check database file exists: `plugins/vTarla/database.db`
2. Verify auto-save is enabled
3. Check file permissions on database
4. Restore from backup if available
5. Check console for save errors

### Multiplier reset

**Problem:** Player multiplier resets to default

**Solutions:**
1. Verify player_multipliers table exists in database
2. Check multiplier tiers haven't changed in config
3. Verify player permission still exists
4. Check database backup

## Configuration Issues

### Invalid YAML syntax

**Problem:** Plugin won't start with config errors

**Solutions:**
1. Check YAML indentation (use spaces, not tabs)
2. Verify all colons are followed by space: `key: value`
3. Use online YAML validator: https://www.yamllint.com/
4. Check for duplicate keys
5. Restore config.yml from default if corrupted

### Color codes not showing

**Problem:** Messages show `&6` instead of gold color

**Solutions:**
1. Verify config is valid YAML
2. Check client chat settings aren't hiding colors
3. Use double quotes in config if needed
4. Test with simple message first

## Getting Help

1. **Check logs:** `plugins/vTarla/logs/vTarla.log`
2. **Enable debug mode:** Set logging level to DEBUG
3. **Collect information:**
   - Java version
   - Paper version
   - vTarla version
   - Error messages (full stack trace)
   - Config (sanitized)
4. **Open GitHub issue** with all information

## Advanced Debug

### Enable debug logging

Edit config.yml:
```yaml
logging:
  level: "DEBUG"
```

### Database inspection

Use SQLite browser to inspect database:
1. Download SQLite Browser
2. Open `plugins/vTarla/database.db`
3. Check tables and data
4. Look for corruption

### Check task status

In console:
```
/task info vTarla
```

Verify all async tasks are running.
