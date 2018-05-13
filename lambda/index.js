const getSnapshots = require("./getSnapshotsImpl");
const creds = require("./creds.json");

exports.handler = (event, context, callback) => {
  const { surveyId } = event.pathParameters;
  const { queryStringParameters: indicatorsFilter } = event;
  const params = {
    creds,
    surveyId,
    indicatorsFilter
  };
  try {
    getSnapshots(params)
      .then(function(response) {
        let respProxy = {
          statusCode: 200,
          body: JSON.stringify(response)
        };
        callback(null, respProxy);
      })
      .catch(function(err) {
        console.error("Error: " + err);
        callback(err);
      });
  } catch (error) {
    callback(error);
  }
};
