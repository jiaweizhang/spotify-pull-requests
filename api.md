# Spotify PR Web API

Web API for Spotify PR

Available on Swaggerhub: https://app.swaggerhub.com/api/jiaweizhang/spotify-pull-requests

## Functionalities

* Create a PR playlist
* Invite Spotify users to join your PR playlist
* All users can add a request to be PRd
** Add via Spotify app to PR\_playlist\_name\_your\_username
** Add via Spotify PR webapp
* All users can vote on PRs
** Majority add - >= 50% must approve (at add-time)
** Consensus add - 100% must approve (at add-time)
** Veto - custom setting that allows up to n% denials over m hours
* Upvote/downvote songs already in playlist
* Evict songs from PR playlist if enough downvotes
