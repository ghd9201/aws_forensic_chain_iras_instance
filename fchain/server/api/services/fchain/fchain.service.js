import l from '../../../common/logger';

const transaction = require('../../../blockchain/transaction/transaction');

class FchainService {
  getObject(req, res) {
    const args = [];

    args.push(req.params.id);
    return Promise.resolve(transaction.queryChainCode(null, 'fchannel', 'fchain',
      args, 'query', 'admin', 'Org1'));
  }

  delete(req, res) {
    const args = [];

    args.push(req.params.id);
    return Promise.resolve(transaction.queryChainCode(null, 'fchannel', 'fchain',
        args, 'delete', 'admin', 'Org1'));
  }

  addEvidence(req, res) {
    l.info(`${this.constructor.name}.byId(${req})`);
    const args = [];
    const peers = [];

    //args.push(req.body.balance.toString());

    args.push(req.query.EvidenceId);
    args.push(req.query.RegisterId);
    args.push(req.query.Description);
    args.push(req.query.CaseId);
    args.push(req.query.Hash);
    args.push(req.query.RegisterTime);

    peers.push('p0.org1.fchain.com');
    /*peers.push('p1.org1.fchain.com');
    peers.push('p0.org2.fchain.com');
    peers.push('p1.org2.fchain.com');
    peers.push('p0.org3.fchain.com');
    peers.push('p1.org3.fchain.com');*/

    l.debug(`invoke peers:${peers}`);
    return Promise.resolve(transaction.invokeChainCode(peers, 'fchannel', 'fchain',
      'addEvidence', args, 'admin', 'Org1'));
  }

  addDocument(req, res) {
    l.info(`${this.constructor.name}.byId(${req})`);
    const args = [];
    const peers = [];

    //args.push(req.body.balance.toString());

    args.push(req.query.DocumentId);
    args.push(req.query.DocumentType);
    args.push(req.query.WriterId);
    args.push(req.query.WriteTime);
    args.push(req.query.Description);
    args.push(req.query.CaseId);
    args.push(req.query.Hash);

    peers.push('p0.org1.fchain.com');
    /*peers.push('p1.org1.fchain.com');
    peers.push('p0.org2.fchain.com');
    peers.push('p1.org2.fchain.com');
    peers.push('p0.org3.fchain.com');
    peers.push('p1.org3.fchain.com');*/

    l.debug(`invoke peers:${peers}`);
    return Promise.resolve(transaction.invokeChainCode(peers, 'fchannel', 'fchain',
        'addDocument', args, 'admin', 'Org1'));
  }
}

export default new FchainService();
