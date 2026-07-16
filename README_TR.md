# vTarla - Minecraft Paper Plugin

## Proje Açıklaması

vTarla, Paper Minecraft sunucusu (1.21+) için geliştirilmiş, yüksek performanslı ve tamamen özelleştirilebilir bir tarla (farm) eklentisidir.

## 🌟 Özellikler

### Ana Özellikler:
- **🌾 Gelişmiş Tarla Sistemi** - Otomatik ekin yenileme ve para kazanma
- **💰 Özel Coin Sistemi** - Async veritabanı depolaması ile reload güvenli
- **📈 Çarpan Sistemi** - Yetki tabanlı katmanlar (Bronze, Silver, Gold, Diamond, Emerald)
- **🎁 Dinamik Market** - GUI ile ürün satın alma ve yönetim
- **💳 İndirim Yönetimi** - Küresel indirim sistemi
- **📊 ActionBar & BossBar** - Gerçek zamanlı bilgi gösterimi
- **💾 SQLite Veritabanı** - Otomatik yedekleme ile veri kalıcılığı
- **⚡ Tamamen Async** - Sıfır TPS etkisi
- **🎨 Tam Özelleştirilebilirlik** - Config.yml ile her şey değişebilir
- **🔐 Yetki Sistemi** - Permission tabanlı erişim kontrolü

## 📦 Kurulum

1. En son JAR dosyasını releases bölümünden indir
2. `plugins` klasörüne koy
3. Sunucuyu yeniden başlat
4. `plugins/vTarla/config.yml` dosyasını ihtiyacına göre ayarla
5. `/tarla reload` komutu ile eklentiyi yeniden yükle

## 🎮 Komutlar

### Oyuncu Komutları
```
/tarla help              - Yardım menüsü
/tarla stats             - İstatistiklerinizi görün
/tarla profile           - Profilinizi göster
/tarla market            - Pazarı aç
/tarla multiplier        - Çarpan menüsünü aç
```

### Admin Komutları
```
/tarla reload            - Eklentiyi yeniden yükle
/taladiscount set <num>  - Küresel indirim ayarla
/taladiscount remove     - İndirimi kaldır
```

## 🔑 Yetkiler

```
tarla.*                  - Tam erişim
tarla.command            - Ana komutu kullan
tarla.market             - Pazara erişim
tarla.multiplier         - Çarpan sistemine erişim
tarla.admin.*            - Tüm admin yetkilerine erişim
tarla.admin.reload       - Eklentiyi yeniden yükle
tarla.admin.discount     - İndirimleri yönet
tarla.admin.multiplier   - Çarpan katmanlarını yönet
```

## ⚙️ Ayarları Yapılandırma

### Tarla Ayarları
```yaml
farm:
  world: "farm_world"               # Tarla dünyasının adı
  auto-save-interval: 300            # Kaydetme aralığı (saniye)
  auto-regen-interval: 600           # Ekin yenileme aralığı
```

### Coin Sistemi
```yaml
coin:
  coins-per-crop: 10                # Ekin başına kazanılan coin
  display-name: "&6Tarla Coin&r"    # Gösterim adı
  earn-message: "&a+{coins} {coin_name}"
```

### Çarpan Katmanları
```yaml
multiplier:
  tiers:
    bronze:
      multiplier: 1.5
      price: 5000
      permission: "tarla.multiplier.bronze"
      display-name: "&8Bronze Multiplier &7(1.5x)"
    # ... daha fazla katman
```

### Market Ürünleri
```yaml
market:
  items:
    diamond:
      name: "&bDiamond Block"
      material: "DIAMOND_BLOCK"
      price: 1000
      amount: 1
      command: "none"
```

## 🏗️ Mimari

- **Async İşleme** - Tüm ağır işlemler asenkron olarak çalışır
- **SQLite Veritabanı** - Kalıcı veri depolama
- **Bellek Önbelleği** - Anlık erişim için hafızada önbelekleme
- **Olay Tabanlı** - Ekin kırma olaylarını dinler ve coin günceller
- **Konfigürasyon Odaklı** - Her şey ayarlanabilir

## ⚡ Performans

- ✅ Async görevlerle sıfır TPS etkisi
- ✅ Otomatik veritabanı temizliği
- ✅ Verimli bellek kullanımı
- ✅ Optimize edilmiş sorgular
- ✅ Reload güvenli - Hiçbir veri kaybı yok

## 📁 Dosya Yapısı

```
vTarla/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/vixians/tarla/
        │   ├── TarlaPlugin.java
        │   ├── config/
        │   ├── database/
        │   ├── coin/
        │   ├── multiplier/
        │   ├── market/
        │   ├── farm/
        │   ├── discount/
        │   ├── commands/
        │   ├── listeners/
        │   └── utils/
        └── resources/
            ├── config.yml
            └── plugin.yml
```

## 🛠️ Gereksinimler

- Java 17+
- Paper 1.21+
- Maven (derleme için)

## 📝 Derleme

```bash
mvn clean package
```

Derlenen JAR dosyası `target/` klasöründe oluşturulacaktır.

## 🐛 Hata Bildirimi

Herhangi bir sorun veya öneriniz varsa, GitHub'da issue açınız.

## 📄 Lisans

MIT Lisansı - Özgürce kullan ve değiştir!

## 👨‍💻 Geliştirici

Vixians - 2026
