var getSnapshots = require("./getSnapshotsImpl");
var creds = require("./creds.json");

const queryString = "security=2&registeredToVoteAndVotesInElections=2";

const indicatorsFilter = {
  security: "2",
  registeredToVoteAndVotesInElections: "2",
  match: "any"
};

const params = {
  creds,
  surveyId: 11,
  indicatorsFilter
};

getSnapshots(params).then(resp => console.log(resp));
