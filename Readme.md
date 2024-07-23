prod :
sudo mkdir -p /var/lib/vault/data
sudo chown -R $USER:$USER /var/lib/vault

sudo chown -R $USER:$USER /var/lib/vault

command : 
docker exec -it vault vault operator init
docker exec -it vault vault operator unseal

-- local
mkdir C:\java\vault\data

