import requests

token = ""
hed = {'Authorization': 'Bearer ' + token}
url = "https://api.pingdom.com/api/3.1/checks"
host = "svagworks.me"
team_id = "495938"

request = {	"name": "app", "host": host,  "type": "http", "teamids": team_id, "resolution": "1", "sendnotificationwhendown": "1", "probe_filters": "region: NA" } 
response = requests.post(url, data=request, headers=hed)
