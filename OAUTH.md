#About OAuth2

The OAuth 2.0 authorization framework enables a third-party application to obtain limited access to an HTTP service, either on behalf of a resource owner by orchestrating an approval interaction between the resource owner and the HTTP service, or by allowing the third-party application to obtain access on its own behalf.

#Usage

##Please run the sql script after run the application

```
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('barClientIdPassword', 'secret', 'bar,read,write',
	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);
```

##Get access token with client credentials

Client id : barClientIdPassword

Client secret : secret

User name : foo

User password : foopass


```
curl -X POST -vu barClientIdPassword:secret 'http://localhost:8080/oauth/token?username=foo&password=foopass&grant_type=password'
```

##Result

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

##Access secure resource with token

```
curl -i -H "Authorization: Bearer [token]" http://localhost:8080/api/v1/people

```

##Result

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
	"phoneNumber": "595999999888",
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