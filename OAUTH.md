#About OAuth2

The OAuth 2.0 authorization framework enables a third-party application to obtain limited access to an HTTP service, either on behalf of a resource owner by orchestrating an approval interaction between the resource owner and the HTTP service, or by allowing the third-party application to obtain access on its own behalf.

#Observation

The Oauth2 security is disabled by default in development enviroment,
this configuration is in the COMMON SPRING BOOT PROPERTIES file.

#Usage

###Get access token with client and user credentials

Client id : barClientIdPassword

Client secret : secret

User name : admin

User password : password

```
curl -X POST -vu barClientIdPassword:secret 'http://localhost:8080/oauth/token?username=admin&password=password&grant_type=password'
```

###Result

```
{
	"access_token": "f27b2bff-ddd8-47fb-9f57-93a245030bc4",
	"token_type": "bearer",
	"refresh_token": "0457e7ab-261b-4e9e-9773-981b180bf3ad",
	"expires_in": 35999,
	"scope": "bar read write",
	"user": {
		"password": null,
		"username": "admin",
		"authorities": [{
			"authority": "USER"
		}],
		"accountNonExpired": true,
		"accountNonLocked": true,
		"credentialsNonExpired": true,
		"enabled": true
	}
}
```

###Access secure resource with token

```
curl -i -H "Authorization: Bearer [access_token]" http://localhost:8080/api/v1/people

```

###Result

```
[{
	"personId": 1,
	"name": "Jhon",
	"lastname": "Cena",
	"identificationType": "CI",
	"identificationNumber": "1223334444",
	"personRole": "ADMIN",
	"gender": "M",
	"activityPrimary": "Actor",
	"activitySecundary": "Ex-Bodybuilder",
	"phoneNumber": "+19999949888",
	"country": {
		"countryId": 1,
		"country": "United States"
	},
	"city": {
		"cityId": 1,
		"city": "West Newbury, Massachusetts"
	},
	"family": null
}]
```