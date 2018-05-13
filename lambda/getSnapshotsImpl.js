var qs = require("qs");
var axios = require("axios");
var request = require("request");

const AUTH_URL = `https://platform.backend.povertystoplight.org/oauth/token`;
const BASE_URL = `https://platform.backend.povertystoplight.org`;

function getSnapshotsBySurvey(accessToken, surveyId, indictarosFilter) {
  const instance = axios.create({
    baseURL: BASE_URL,
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

function login(creds) {
  const options = {
    method: "POST",
    auth: { username: "barClientIdPassword", password: "secret" },
    data: qs.stringify(creds),
    url: AUTH_URL
  };

  return axios(options);
}

function getSnapshotsImpl({ creds, surveyId, indicatorsFilter }) {
  return login(creds).then(resp =>
    getSnapshotsBySurvey(resp.data.access_token, surveyId, indicatorsFilter)
  );
}

module.exports = getSnapshotsImpl;
