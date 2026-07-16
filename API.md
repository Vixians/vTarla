# API Documentation

## vTarla Plugin API

### Getting Started

```java
import com.vixians.tarla.api.TarlaAPI;
```

### Coin Management

#### Get Player Coins
```java
long coins = TarlaAPI.getPlayerCoins(player);
```

#### Add Coins
```java
TarlaAPI.addPlayerCoins(player, 1000);
```

#### Remove Coins
```java
TarlaAPI.removePlayerCoins(player, 500);
```

#### Check If Player Has Coins
```java
if (TarlaAPI.hasCoins(player, 1000)) {
    // Player has at least 1000 coins
}
```

### Multiplier Management

#### Get Player Multiplier
```java
long multiplier = TarlaAPI.getPlayerMultiplier(player);
```

#### Get Multiplier Tier
```java
String tier = TarlaAPI.getPlayerMultiplierTier(player);
```

#### Set Multiplier
```java
boolean success = TarlaAPI.setPlayerMultiplier(player, "gold");
```

### Discount Management

#### Get Current Discount
```java
int discount = TarlaAPI.getCurrentDiscount();
```

#### Set Discount
```java
TarlaAPI.setDiscount(20, "Admin");
```

### Farm World

#### Get Farm World Name
```java
String farmWorld = TarlaAPI.getFarmWorld();
```

## Events

### CoinEarnEvent

Called when a player earns coins.

```java
@EventHandler
public void onCoinEarn(CoinEarnEvent event) {
    Player player = event.getPlayer();
    long amount = event.getAmount();
    long multipliedAmount = event.getMultipliedAmount();
}
```

### MultiplierPurchaseEvent

Called when a player purchases a multiplier.

```java
@EventHandler
public void onMultiplierPurchase(MultiplierPurchaseEvent event) {
    Player player = event.getPlayer();
    String tier = event.getTier();
    double multiplier = event.getMultiplier();
    long price = event.getPrice();
}
```

### MarketPurchaseEvent

Called when a player makes a market purchase.

```java
@EventHandler
public void onMarketPurchase(MarketPurchaseEvent event) {
    Player player = event.getPlayer();
    String itemKey = event.getItemKey();
    long price = event.getPrice();
}
```

## Dependencies

To use vTarla API in your plugin, add this to your `pom.xml`:

```xml
<dependency>
    <groupId>com.vixians</groupId>
    <artifactId>vTarla</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

And add vTarla as a soft depend in your `plugin.yml`:

```yaml
softdepend:
  - vTarla
```
