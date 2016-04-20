const React = require('React');
const activeComponent = require('react-router-active-component');
const { Link } = require('react-router');

module.exports = React.createClass({
  propTypes: {
    currentIdentity: React.PropTypes.any.isRequired,
    userIdentities: React.PropTypes.array.isRequired,
    switchUserIdentity: React.PropTypes.func.isRequired
  },
  onIdentityClick(publicKey, name) {
    return (() =>
      this.props.switchUserIdentity({publicKey, name})
    );
  },
  render() {
    const {name: currentName, publicKey: currentpublicKey} = this.props.currentIdentity;
    return (
      <li className="dropdown">
        <a href="#" className="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          {currentName} <span className="caret"></span>
        </a>
        <ul className="dropdown-menu">
          {
            this.props.userIdentities
            .filter(({publicKey}) => (publicKey !== currentpublicKey))
            .map(({name, publicKey}) =>
              <li key={publicKey} data-public-key={publicKey} data-name={name} onClick={this.onIdentityClick(publicKey, name)}>
                <a href="#">{name}</a>
              </li>
          )}
          <li role="separator" className="divider"></li>
          <li><Link to="/newIdentity">Create new identity</Link></li>
          <li role="separator" className="divider"></li>
          <li><Link to="/currentKey">Your key</Link></li>
        </ul>
      </li>
    );
  }
});
