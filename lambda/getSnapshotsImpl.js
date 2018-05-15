var qs = require("qs");
var axios = require("axios");
var request = require("request");
const config = require("./config");

function getSnapshotsBySurvey(accessToken, surveyId, indictarosFilter) {
  const instance = axios.create({
    baseURL: config.BASE_URL,
    headers: { Authorization: `Bearer ${accessToken}` }
  });

  return instance
    .get(`/api/v1/snapshots/survey/${surveyId}`)
    .then(resp => filter(resp.data, indictarosFilter))
    .catch(error => Promise.reject(error));
}

function filter(snapshots, { match, ...indicators }) {
  if (!indicators || Object.keys(indicators).length === 0) {
    return snapshots;
  }
  const matchAll = !match || match === "all";
  return snapshots.filter(snapshot => {
    const totalIndicatorsEqual = Object.keys(snapshot)
      .filter(key => Object.keys(indicators).includes(key))
      .reduce((totalEqual, key) => {
        if (parseInt(indicators[key], 10) === snapshot[key]) {
          return totalEqual + 1;
        }
        return 0;
      }, 0);
    if (matchAll) {
      return totalIndicatorsEqual === Object.keys(indicators).length;
    }
    return totalIndicatorsEqual > 0;
  });
}

function getCreds() {
  return {
    username: config.username,
    password: config.password,
    grant_type: config.grant_type,
    clientId: config.clientId,
    clientSecret: config.clientSecret
  };
}

function login(creds = getCreds()) {
  const { clientId, clientSecret, ...restCreds } = creds;
  const options = {
    method: "POST",
    auth: { username: clientId, password: clientSecret },
    data: qs.stringify(restCreds),
    url: config.AUTH_URL
  };

  return axios(options);
}

function getSnapshotsImpl({ surveyId, indicatorsFilter }) {
  return login().then(resp =>
    getSnapshotsBySurvey(resp.data.access_token, surveyId, indicatorsFilter)
  );
}

module.exports = getSnapshotsImpl;
