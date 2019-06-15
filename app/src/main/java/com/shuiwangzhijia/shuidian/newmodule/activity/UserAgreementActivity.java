package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.view.BaseBar;

import butterknife.BindView;

@FndViewInject(contentViewId = R.layout.activity_user_agreement, title = "用户协议")
public class UserAgreementActivity extends BaseActivity {

    @BindView(R.id.content)
    TextView mContent;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        mContent.setText("提示条款\n" +
                "为了更好的提供服务及保障双方的权益，深圳银米科技有限公司制定了《“水的快递”平台用户协议》（下称“本协议”）。欢迎用户与深圳银米科技有限公司有限公司（以下简称“银米科技”）共同签署本协议并使用“水的快递”平台提供的服务！\n" +
                "请用户务必审慎阅读、充分理解本协议各条款内容，尤其是免除或者限制责任的条款、法律适用和争议解决条款。用户应重点阅读以粗体下划线标识内容。如用户对本协议内容或页面提示信息等有任何疑问，请勿进行下一步操作。用户可以通过“水的快递”平台的客服或官方热线18565676759进行咨询，以便水的快递客服为用户解释和说明。\n" +
                "当用户按照提示填写信息、阅读并同意本协议且完成全部注册程序后，即表示用户已充分阅读、理解并接受本协议的全部内容，并与银米科技达成一致，成为“水的快递”平台的用户。阅读本协议的过程中，如果用户不同意本协议或其中任何条款约定，用户应立即停止注册程序。\n" +
                "由于互联网高速发展，本协议列明的条款不能完整罗列并覆盖用户与XXXX所有权利与义务，现有的约定也不能保证完全符合未来发展的需求。因此，“水的快递”平台发布的对本协议的变更或补充约定是本协议不可分割的一部分，且与本协议具有同等法律效力，如用户继续使用“水的快递”平台服务，视为用户同意上述变更或补充约定。\n" +
                "如用户为无民事行为能力人或为限制民事行为能力人，请在用户监护人的指导下阅读本协议和使用水的快递的服务。若用户非中华人民共和国境内用户，用户还需同时遵守用户所属国家或地区的法律，且用户确认，订立并履行本协议不违反用户所属、所居住或开展经营活动或其他业务的国家或地区的法律法规。\n" +
                "本协议自用户完成注册起生效。\n" +
                "一、\t定义条款\n" +
                "1.1\t“水的快递”平台：深圳银米科技有限公司运营和管理的互联网交易平台，包括水的快递（域名为             ）网站和/或客户端、微信商城、微信小程序、APP等。\n" +
                "1.2\t“水的快递”平台服务：水的快递基于互联网，以“水的快递”平台、客户端等形态（包括未来技术发展出现的新的服务形态）向用户提供的促成用户与商户达成购水交易等各项服务。\n" +
                "1.3\t管理员：“水的快递”平台运营者的单位或合称，包括深圳银米科技有限公司等。\n" +
                "1.4\t商户：通过“水的快递”平台发布商品和/或服务信息、向用户提供订购商品和/或服务的自然人、法人或其他组织。\n" +
                "1.5\t用户：接受并同意本协议全部条款以及“水的快递”平台发布的其他全部服务条款和操作规则并成功注册的 “水的快递”平台会员。\n" +
                "1.6\t商品信息：商户通过“水的快递”平台发布或展示的包括但不限于商品/服务的名称、规格、数量、价格、有效期、商户地址、配送方式、退换货方式、退款条件、售后服务等内容。\n" +
                "商品信息在法律上构成商户就订购商品/服务向用户发出的要约。\n" +
                "二、\t用户账号注册、使用及注销\n" +
                "2.1\t账号注册\n" +
                "2.1.1\t注册资格\n" +
                "2.1.1.1\t用户具有完全民事权利能力和民事行为能力，或虽不具有完全民事权利能力和民事行为能力但经其法定代理人同意并由其法定代理人代理注册及应用“水的快递”平台服务。\n" +
                "2.1.1.2\t如不具备注册资格，用户应立即停止在“水的快递”平台的注册程序并停止使用“水的快递”平台服务。\n" +
                "2.1.1.3\t若违反前述规定注册使用“水的快递”平台服务，银米科技有权随时终止用户的注册进程及平台服务，并保留追究用户法律责任的权利。\n" +
                "2.1.2\t注册流程\n" +
                "2.1.2.1\t用户同意按照“水的快递”平台用户注册页面的要求提供有效的身份证号码、手机号码、电子邮箱、联系地址等信息，设置“水的快递”平台账号及密码。\n" +
                "2.1.2.2\t用户应确保所提供全部信息的真实性、有效性、完整性和准确性，不存在盗用、冒用非本人身份证号码、手机号码等信息进行注册、虚假交易和作弊交易的行为。\n" +
                "2.1.2.3\t用户若存在上述违规行为，银米科技有权立即终止服务、封停账号。如该违规行为给“水的快递”平台造成损失的，“水的快递”平台保留追究赔偿及诉至法律解决的权利。\n" +
                "2.1.2.4\t用户提供注册所需全部合法、有效信息后，有权获得“水的快递”平台账号，用户可自行设置账号登录密码，用户激活后可使用该账号和密码登录“水的快递”平台并使用“水的快递”平台提供的服务。\n" +
                "2.1.2.5\t用户获得“水的快递”平台账号及密码时视为用户注册成功，用户同意接收“水的快递”平台发送的与“水的快递”平台管理、运营相关的电子邮件和/或短消息。\n" +
                "2.1.2.6\t用户有权在注册时选择是否订阅“水的快递”平台发送的关于商品信息的电子邮件或短消息，并在注册成功后有权随时订阅或退订“水的快递”平台该等信息。\n" +
                "2.1.2.7\t用户在账号中设置的昵称、头像、签名、留言等应遵守法律法规、公序良俗、社会公德，且不会侵害任何第三方的合法权益，否则银米科技可能会取消用户的昵称、头像、签名。\n" +
                "2.2\t账号使用\n" +
                "身份要素是“水的快递”平台识别用户身份的依据，请用户务必妥善保管。使用身份要素进行的操作、发出的指令均视为用户本人做出。因用户的原因造成账户、密码等信息被冒用、盗用或非法使用，由此引起的风险和损失用户需自行承担。用户同意：\n" +
                "2.2.1\t基于不同的终端以及用户的使用习惯，“水的快递”平台可能采取不同的验证措施识别用户的身份。例如用户的“水的快递”平台账号在新设备首次登录的，“水的快递”平台可能通过密码加校验码的方式识别用户的身份。\n" +
                "2.2.2\t如用户发现有他人冒用、盗用用户的账户及密码，或用户的手机等有关设备丢失时，请用户立即以有效方式通知“水的快递”平台客服；用户还可以向平台客服申请暂停或停止服务。由于“水的快递”平台客服对用户的请求采取行动需要合理时间，如平台客服未在合理时间内采取有效措施，导致用户损失扩大的，“水的快递”平台将就扩大的损失部分承担责任，但“水的快递”客服对采取行动之前已执行的指令免于承担责任。\n" +
                "2.2.3\t“水的快递”平台账号、密码仅限用户本人使用，请勿转让、借用、赠与、继承，但“水的快递”平台账号内的相关财产权益可被依法继承。\n" +
                "2.2.4\t基于“水的快递”平台运行管理和商业模式的需要，银米科技可能会暂停、限制或者增加“水的快递”平台功能。\n" +
                "2.2.5\t用户在“水的快递”平台上发生的所有交易，均按照本协议以及“水的快递”平台制定的有关规则进行处理。同时，为了解决用户的交易纠纷，用户不可撤销地授权“水的快递”平台可按照本协议的指令将争议款项的全部或部分支付给交易一方或双方。\n" +
                "2.3\t账号注销\n" +
                "2.3.1\t用户在需要终止使用“水的快递”平台服务时，符合以下条件的，可以申请注销“水的快递”平台账号：\n" +
                "2.3.1.1\t用户仅能申请注销用户本人的账号，并依照“水的快递”平台的流程进行注销。\n" +
                "2.3.1.2\t用户可以通过自助或者人工的方式申请注销“水的快递”平台账号。\n" +
                "2.3.1.3\t用户申请注销的“水的快递”平台账号处于正常状态，即用户的账号的信息是最新、完整、正确的，且账号未被采取冻结等限制措施。\n" +
                "2.3.1.4\t为了维护用户的合法利益，用户申请注销的“水的快递”平台账号，应当不存在未了结的权利义务或其他因为注销该账户会产生纠纷的情况，不存在未完结交易。\n" +
                "2.3.2\t用户在使用“水的快递”平台服务时，如有下列情形之一的，“水的快递”管理员有权暂停或注销用户的“水的快递”平台账号：\n" +
                "2.3.2.1\t为了防止资源占用，如用户连续12个月未使用用户的账号或其他“水的快递”平台不认可的方式登录“水的快递”平台，平台管理员可能会对该账号进行注销，用户将不能再通过该账号登录“水的快递”平台。如该账号在“水的快递”平台有待处理交易或余额，“水的快递”客服会协助用户处理，请用户按照平台客服提示的方式进行操作。\n" +
                "2.3.2.2\t为了维护良好的网络环境，如用户自开立“水的快递”平台账号之日起6个月内无交易，则向用户重新核实身份之前，“水的快递”平台管理员可以暂停或者注销该账号。\n" +
                "2.3.2.3\t如用户在“水的快递”平台有欺诈、侵犯他人合法权益或其他严重违反法律法规和/或“水的快递”平台规则的行为，“水的快递”平台管理员可以注销用户名下的“水的快递”平台账号。\n" +
                "2.3.2.4\t“水的快递”平台账号注销后，用户将无法使用“水的快递”的服务，双方的权利义务终止（本协议另有约定不得终止的或依其性质不能终止的除外），同时还可能产生如下结果：\n" +
                "2.3.2.4.1\t与“水的快递”平台关联的权益（如优惠券、积分等）均将作废；\n" +
                "2.3.2.4.2\t如用户在注销账号前存在违约、侵权等不当行为或未完结义务的，用户仍应承担相应责任；\n" +
                "2.3.2.4.3\t一旦注销成功，账户记录、账户功能等将无法恢复或提供。\n" +
                "\n" +
                "三、\t用户信息的保护及授权\n" +
                "3.1\t个人信息的保护\n" +
                "3.1.1\t“水的快递”平台非常重视用户个人信息（即能够独立或与其他信息结合后识别用户身份的信息）的保护，用户在使用“水的快递”平台提供的服务时，用户同意按照“水的快递”平台的隐私权政策收集、存储、使用、披露和保护用户的个人信息。\n" +
                "3.1.2\t用户声明并保证，用户对其所发布的信息拥有相应、合法的权利。否则，“水的快递”平台可对用户发布的信息依法或依本协议进行删除或屏蔽。\n" +
                "3.2\t用户应当确保用户所发布的信息不包含以下内容：\n" +
                "3.2.1\t违反国家法律法规禁止性规定的；\n" +
                "3.2.2\t政治宣传、封建迷信、淫秽、色情、赌博、暴力、恐怖或者教唆犯罪的；\n" +
                "3.2.3\t欺诈、虚假、不准确或存在误导性的；\n" +
                "3.2.4\t侵犯他人知识产权或涉及第三方商业秘密及其他专有权利的；\n" +
                "3.2.5\t侮辱、诽谤、恐吓、涉及他人隐私等侵害他人合法权益的；\n" +
                "3.2.6\t存在可能破坏、篡改、删除、影响“水的快递”平台系统正常运行或未经授权秘密获取“水的快递”平台及其他用户的数据、个人资料的病毒、木马、爬虫等恶意软件、程序代码的；\n" +
                "3.2.7\t其他违背社会公共利益或公共道德或依据“水的快递”平台相关协议、规则的规定不适合在“水的快递”平台上发布的。\n" +
                "3.3\t用户提供、发布及在使用“水的快递”平台服务中形成的文字、图片、视频、音频等非个人信息的著作权及相关权利，在法律规定的保护期限内用户免费授予“水的快递”平台获得全球排他的许可使用权利及再授权给其他第三方使用并可以自身名义对第三方侵权行为取证及提起诉讼的权利。用户同意“水的快递”平台存储、使用、复制、修订、编辑、发布、展示、翻译、分发用户的非个人信息或制作其派生作品，并以已知或日后开发的形式、媒体或技术将上述信息纳入其它作品内。\n" +
                "四、\t用户服务\n" +
                "“水的快递”平台为用户订购交易活动提供网络交易平台服务，目前“水的快递”平台为用户提供的服务为免费服务，但“水的快递”平台保留未来对该平台服务收取服务费用的权利。\n" +
                "4.1\t服务内容\n" +
                "4.1.1\t用户有权在“水的快递”平台浏览商品/服务的信息，并通过“水的快递”平台与商户达成订单、支付订购价款、获得电子消费凭证（如有）等。\n" +
                "4.1.2\t用户有权在“水的快递”平台查看其账号信息，并应用“水的快递”平台提供的功能进行相关操作。\n" +
                "4.1.3\t用户有权按照“水的快递”平台发布的活动规则参与“水的快递”平台组织的网站活动。\n" +
                "4.1.4\t“水的快递”平台承诺为用户提供的其他服务。\n" +
                "4.2\t服务规则\n" +
                "用户承诺遵守“水的快递”平台下列服务规则：\n" +
                "4.2.1\t用户应当遵守法律法规、规章、规范性文件及政策要求的规定，不得在“水的快递”平台或利用“水的快递”平台服务从事非法或其他损害“水的快递”平台或第三方权益的活动，如发送或接收任何违法、违规、违反公序良俗、侵犯他人权益的信息，发送或接收传销材料或存在其他危害的信息或言论，未经“水的快递”平台授权使用或伪造“水的快递”平台电子邮件题头信息等。\n" +
                "4.2.2\t用户应当遵守法律法规应当谨慎合理使用和妥善保管“水的快递”平台账号及密码，对其“水的快递”平台账号和密码下进行的行为和发生的事件负责。\n" +
                "4.2.3\t用户通过“水的快递”平台与商户进行订购交易时，应当遵守本协议及“水的快递”平台发布的其他关于订购交易的服务条款和操作规则的全部规定。\n" +
                "4.2.4\t用户有权在“水的快递”平台发布对订购商品/服务的购买体验、评价等信息。用户发布的信息，均仅代表用户观点，与“水的快递”平台无关，用户对其言论独立承担全部法律责任。\n" +
                "4.2.5\t用户应当按照“水的快递”平台发布的规则参加“水的快递”平台的各类活动，遵守活动秩序。\n" +
                "4.2.6\t“水的快递”平台发布的其他服务条款和操作规则。\n" +
                "五、\t订购交易规则\n" +
                "用户承诺其通过“水的快递”平台与商户进行订购交易的过程中严格遵守“水的快递”平台如下订购交易规则。\n" +
                "5.1\t浏览商品/服务信息\n" +
                "用户在“水的快递”平台浏览订购商品/服务的信息时，应当仔细阅读商品展示页面所包含的全部信息内容，包括但不限于订购商品/服务的名称、种类、数量、质量、价格、有效期、配送方式、退换货的方式、售后服务等内容，用户完全接受商品/服务信息中包含的全部内容后方可点击购买。\n" +
                "5.2\t确认提交订单\n" +
                "5.2.1\t用户应当仔细阅读订单页面中所包含的全部内容，包括但不限于商品信息中的全部内容、选择及确认购买数量、价格、应付总额、用户接收电子消费凭证的联系方式或接收货物的收货地址和送货时间等内容。\n" +
                "5.2.2\t前述订单页面中所包含的全部内容，构成了用户与商户之间达成的订购合同的合同内容，用户完全同意订单的全部内容后方可提交订单，用户提交订单的行为视为已完整阅读并认可全部内容。\n" +
                "5.3\t支付订单费用\n" +
                "5.3.1\t在订购合同成立之后用户应根据付款页面的提示通过网上支付平台完成订购价款的支付。\n" +
                "5.3.2\t商户委托“水的快递”平台代商户收取订单费用，故用户向“水的快递”平台成功支付订单费用即视为用户已向商户履行了订购合同项下的付款义务。\n" +
                "5.3.3\t用户在支付订购价款之前无权要求商户向用户提供订购商品/服务。\n" +
                "5.4\t电子消费凭证\n" +
                "5.4.1\t用户成功支付订单费用后，“水的快递”平台向用户发送电子消费凭证，用户可按照凭该电子消费凭证向商户主张获得订购商品/服务。\n" +
                "5.4.2\t用户应当妥善保管电子消费凭证，因用户保管不善导致电子消费凭证被他人使用的，用户无权要求“水的快递”平台重新发送电子消费凭证。\n" +
                "5.4.3\t用户进行消费时，应向商户出示电子消费凭证，商户对电子消费凭证验证成功后按照订购合同内容的约定向用户提供订购商品/服务。\n" +
                "5.4.4\t电子消费凭证于发生以下情形之一时即失效\n" +
                "5.4.4.1\t凭电子消费凭证已获得订购商品/服务；\n" +
                "5.4.4.2\t 订购合同内容中约定的有效期届满。\n" +
                "5.4.4.3\t用户于商户提供服务前取消订单。\n" +
                "5.5\t退款规则\n" +
                "用户付款成功后，需要进行退款的，按照如下规则进行：\n" +
                "5.5.1\t发生以下情形之一的，用户有权要求“水的快递”平台代商户进行退款：\n" +
                "5.5.1.1\t用户付款成功后，因不可抗力或商户原因，导致商户无法向用户提供订购商品/服务，经“水的快递”平台核实后属实的；\n" +
                "5.5.1.2\t用户付款成功后，因确属情况变化导致商户需要变更订购合同内容，用户不接受变更的。\n" +
                "5.5.2\t用户已实际消费订购商品/服务的，无权要求商户退款或要求“水的快递”平台代商户进行退款，但订购商品/服务与约定内容严重不符、存在其他严重质量问题或违反《中华人民共和国食品卫生法》、《产品质量法》、《中华人民共和国消费者权益保护法》的除外。\n" +
                "5.5.3\t用户根据有关约定要求退款的，用户应按照订单及有关约定要求“水的快递”平台代商户进行退款。如用户未向“水的快递”平台要求退款的，即视为用户放弃了主张退款的权利，“水的快递”平台保留处理此等款项的权利。\n" +
                "5.5.4\t用户提交订单并付款后，未凭电子消费凭证进行实际消费且符合“水的快递”平台相关约定的，用户可以向“水的快递”平台申请退款。\n" +
                "5.5.5\t经“水的快递”平台审核，用户的退款申请符合相关约定的，“水的快递”平台将于5个工作日内退款至用户“水的快递”平台账户内，如下商品或服务除外：\n" +
                "5.5.5.1\t用户已消费且无充分证据证明商户提供的商品/服务存在瑕疵或与订单、商品信息不符的；\n" +
                "5.5.5.4\t用户按照“水的快递”平台规则提交退货申请后，货物退回商户前（需物流运送的产品）；\n" +
                "5.5.5.4\t因用户非正常使用及保管而损坏的商品；\n" +
                "5.5.5.4\t用户提交订单前“水的快递”平台中已明确标明“不支持未消费随时退款”的产品或服务；\n" +
                "5.3\t订单争议解决规则\n" +
                "如用户与商户因商品/服务发生任何争议，用户应与商户根据订单内容及相关约定确定各自的权利义务，并承担相应的责任。“水的快递”平台可协助用户与商户协商调解争议。\n" +
                "六、\t用户的权利与义务\n" +
                "6.1\t用户有权按照本协议约定接受“水的快递”平台提供的各类服务。\n" +
                "6.2\t用户有权要求提供订购商品/服务的发票、其他付款凭证、购货凭证或服务单据，发票金额以实际支付的订购价款为准。\n" +
                "6.3\t用户有权随时终止使用“水的快递”平台服务。\n" +
                "6.4\t用户使用商品/服务的过程中，如发现订购商品/服务与订单内容不符或存在质量、服务态度等问题的，应与商户协商解决，“水的快递”平台提供协助。\n" +
                "6.5\t用户应保证其在“水的快递”平台提供的姓名、联系方式、联系地址等信息均真实、有效、完整、准确，当上述信息发生变更时，应及时在“水的快递”平台上进行更新。\n" +
                "6.6\t用户在“水的快递”平台进行交易时不得恶意干扰交易的正常进行、破坏“水的快递”平台秩序。\n" +
                "6.7\t用户不得以任何技术手段或其他方式干扰“水的快递”平台的正常运行或干扰其他用户使用“水的快递”平台。\n" +
                "6.8\t用户不得捏造、虚构事实等恶意诋毁“水的快递”平台或商户的商誉。\n" +
                "6.9\t用户通过“水的快递”平台进行交易应出于真实消费目的，不得以转售等商业目的进行订购交易。\n" +
                "6.10\t用户在付款成功后应配合接收货物或电子消费凭证。\n" +
                "6.11\t用户不得对订购商品/服务进行虚假评价或虚假投诉。\n" +
                "七、\t“水的快递”平台的权利与义务\n" +
                "7.1\t若用户不符合本协议约定的注册资格，则“水的快递”平台有权拒绝用户进行注册。用户已经注册的，“水的快递”平台有权注销其账号并保留追究该用户或其法定代理人赔偿损失的权利。 “水的快递”平台有权在任何情况下决定是否接受用户的注册。\n" +
                "7.2\t“水的快递”平台发现账户使用者并非账户注册人时，有权中止该账户的使用。\n" +
                "7.3\t“水的快递”平台通过技术检测、人工抽检等检测方式合理怀疑用户提供的信息错误、不实、失效或不完整时，有权通知用户更正、更新信息或中止、终止为其提供“水的快递”平台服务。\n" +
                "7.4\t“水的快递”平台有权在发现“水的快递”平台上显示的任何信息存在明显错误时，对信息予以更正。\n" +
                "7.5\t用户付款成功前，“水的快递”平台有权接受商户委托对订单内容作出变更，如用户接受变更后的内容则用户可确认订单及付款，如用户不接受变更后内容则有权取消订单\n" +
                "7.6\t“水的快递”平台保留随时修改、中止或终止“水的快递”平台服务的权利，“水的快递”平台行使修改或中止服务的权利不需事先告知用户，“水的快递”平台终止“水的快递”平台一项或多项服务的，终止自“水的快递”平台在网站上发布终止公告之日生效。\n" +
                "7.7\t“水的快递”平台应当采取必要的技术手段和管理措施保障“水的快递”平台的正常运行，并提供必要、可靠的交易环境和交易服务，维护平台交易秩序。\n" +
                "7.8\t“水的快递”平台有权根据本协议约定情形注销用户的“水的快递”平台账号。账号注销后，“水的快递”平台有权将该账号开放给其他用户注册使用。\n" +
                "7.9\t“水的快递”平台有权在本协议履行期间及本协议终止后保留用户的注册信息及用户使用“水的快递”平台服务期间的全部交易信息，但不得非法使用该等信息。\n" +
                "7.10\t“水的快递”平台有权随时删除“水的快递”平台网站内各类不符合国家法律法规、规范性文件或“水的快递”平台网站规定的用户评价等信息，“水的快递”平台行使该等权利不需提前通知用户。\n" +
                "八、\t知识产权\n" +
                "8.1\t“水的快递”平台的系统及网站上的内容，包括但不限于数据库、网站架构、网站设计、文字和图表、照片、录像、音乐、声音及其前述组合，软件编译、相关源代码和软件(包括小应用程序和脚本)档案、资讯、资料的知识产权均由“水的快递”平台所有。\n" +
                "8.2\t“水的快递”平台名称中包含的所有权利(包括商誉和商标)均归“水的快递”平台所有。\n" +
                "8.3\t非经银米科技的书面同意，请勿擅自使用、修改、反向编译、复制、公开传播、改变、散布、发行或公开发表“水的快递”平台上述任何材料或内容。\n" +
                "8.4\t用户在使用“水的快递”平台服务过程中，用户如违反上述知识产权保护内容的，需要承担损害赔偿责任。\n" +
                "九、\t协议的变更和终止\n" +
                "9.1\t协议的变更\n" +
                "9.1.1\t银米科技可根据国家法律法规变化及维护交易秩序、保护消费者权益需要，不时修改本协议或增加补充协议，变更后的协议及增加的补充协议（下称“变更规定”）将通过法定程序并以本协议约定的方式通知用户。\n" +
                "9.1.2\t如用户不同意变更规定，用户有权于变更规定确定的生效日前向“水的快递”平台客服反馈意见。如反馈意见得以采纳，银米科技将酌情调整变更规定。\n" +
                "9.1.3\t如用户对已生效的变更规定仍不同意的，用户应当于变更规定确定的生效之日起停止使用“水的快递”平台服务，变更规定对用户不产生效力；如用户在变更规定生效后仍继续使用“水的快递”平台服务，则视为用户同意已生效的变更规定。\n" +
                "9.2\t协议的终止\n" +
                "9.2.1\t用户有权通过以下任一方式终止本协议：\n" +
                "9.1.2.1\t在满足“水的快递”平台公示的账号注销条件时，用户通过网站注销其“水的快递”平台账号的；\n" +
                "9.1.2.2\t变更规定生效前用户停止使用并明示不愿接受变更规定的；\n" +
                "9.1.2.3\t用户明示不愿继续使用“水的快递”平台服务，且符合“水的快递”平台终止条件的。\n" +
                "9.2.2\t出现以下情况时，银米科技可终止本协议，并以本协议约定方式通知用户：\n" +
                "9.2.2.1\t用户违反本协议约定，银米科技依据违约条款终止本协议的；\n" +
                "9.2.2.2\t用户盗用他人账号、发布违禁信息、骗取他人财物、扰乱市场秩序、采取不正当手段谋利等行为，依据“水的快递”平台规则对用户的账号予以查封的；\n" +
                "9.2.2.3\t用户多次违反“水的快递”平台规则及相关规定且情节严重，依据“水的快递”平台规则及相关规定对用户的账号予以查封的；\n" +
                "9.2.2.4\t用户的账号被“水的快递”平台依据本协议回收的；\n" +
                "9.2.2.5\t用户在“水的快递”平台有欺诈、发布虚假信息、侵犯他人合法权益或其他严重违法违约行为的；\n" +
                "9.2.2.6\t其它应当终止服务的情况。\n" +
                "9.2.3\t本协议终止后，除法律有明确规定外，“水的快递”平台无义务向用户或用户指定的第三方披露用户账号中的任何信息。\n" +
                "9.2.4\t本协议终止后，“水的快递”平台仍享有下列权利：\n" +
                "9.2.4.1\t继续保存用户留存于“水的快递”平台的各类信息；\n" +
                "9.2.4.2\t对于用户过往的违约行为，“水的快递”平台仍可依据本协议向用户追究违约责任。\n" +
                "9.2.5\t本协议终止后，对于用户在本协议存续期间产生的交易订单，“水的快递”平台可通知交易相对方并根据交易相对方的意愿决定是否关闭该等交易订单；如交易相对方要求继续履行的，则用户应当就该等交易订单继续履行本协议及交易订单的约定，并承担因此产生的任何损失或增加的任何费用。\n" +
                "十、\t违约及处理措施\n" +
                "10.1\t违约认定\n" +
                "如用户使用“水的快递”平台过程中，存在如下情形之一的，视为用户违约：\n" +
                "10.1.1\t使用“水的快递”平台服务时违反有关法律法规规定的；\n" +
                "10.1.2\t违反本协议或本协议变更规定相关约定的。\n" +
                "10.1.3\t为适应电子商务发展和满足海量用户对高效优质服务的需求，用户理解并同意，银米科技可在“水的快递”平台规则中约定违约认定的程序和标准。如：银米科技可依据用户的用户数据与海量用户数据的关系来认定用户是否构成违约；用户有义务对用户的数据异常现象进行充分举证和合理解释，否则将被认定为违约。\n" +
                "10.2\t违约处理措施\n" +
                "10.2.1\t用户在“水的快递”平台上发布的信息构成违约的，银米科技可根据相应规则立即对相应信息进行删除、屏蔽处理。\n" +
                "10.2.2\t用户在“水的快递”平台上实施的行为，或虽未在“水的快递”平台上实施但对“水的快递”平台及其用户产生影响的行为构成违约的，银米科技依据相应规则对用户执行限制参加营销活动、中止向用户提供部分或全部服务、划扣违约金等处理措施。如用户的行为构成根本违约的，银米科技查封用户的账号，终止向用户提供服务。\n" +
                "10.2.3\t银米科技可将对用户上述违约行为处理措施信息以及其他经国家行政或司法机关生效法律文书确认的违法信息在“水的快递”平台上予以公示。\n" +
                "10.3\t赔偿责任\n" +
                "10.3.1\t如用户违反本协议或相关规定的，用户应赔偿银米科技因此遭受的全部损失（包括但不限于直接经济损失、商誉损失及对外支付的赔偿金、和解款、律师费、诉讼费等间接经济损失）。\n" +
                "10.3.2\t如用户的行为使银米科技遭受第三人主张权利，银米科技可在对第三人承担责任后就全部损失向用户追偿。\n" +
                "10.3.3\t如因用户的行为使得第三人遭受损失或用户怠于履行判决、裁定等，银米科技出于社会公共利益保护或消费者权益保护目的，用户同意委托银米科技代用户支付上述款项，用户应当返还该部分费用并赔偿银米科技因此遭受的全部损失。\n" +
                "十一、\t特别约定\n" +
                "11.1\t如用户向银米科技的雇员或顾问等提供实物、现金、现金等价物、劳务、旅游等价值明显超出正常商务洽谈范畴的利益，则可视为用户存在商业贿赂行为。发生上述情形的，银米科技可立即终止与用户的所有合作并向用户收取违约金及/或赔偿金，该等金额以银米科技因用户的贿赂行为而遭受的经济损失和商誉损失作为计算依据。\n" +
                "11.2\t如用户因严重违约导致银米科技终止本协议时，出于维护平台秩序及保护消费者权益的目的，银米科技可采取中止或终止与用户在其他协议项下的合作。\n" +
                "11.3\t如银米科技与用户签署的其他协议中明确约定了对用户在本协议项下合作进行关联处理的情形，则银米科技出于维护平台秩序及保护消费者权益的目的，可在收到指令时中止或终止协议。\n" +
                "十二、\t不可抗力、免责及责任限制\n" +
                "12.1\t银米科技依照法律规定履行基础保障义务，但因下述原因导致“水的快递”平台无法正常提供服务（包括但不限于履行障碍、履行瑕疵、履行延后或履行内容变更等），银米科技将免于承担责任：\n" +
                "12.1.1\t因自然灾害、罢工、暴乱、战争、政府行为、司法行政命令等不可抗力因素；\n" +
                "12.1.2\t在银米科技已尽善意管理的情况下，因常规或紧急的设备与系统维护、设备与系统故障、网络信息与数据安全等因素。\n" +
                "12.1.3\t银米科技定期或不定期地进行必要的系统停机维护或升级；\n" +
                "12.1.4\t用户的电脑软硬件和通信线路、供电线路出现故障的；\n" +
                "12.1.5\t因用户操作不当或通过非经银米科技授权或认可的方式使用“水的快递”平台服务的；\n" +
                "12.1.6\t因病毒、木马、恶意程序攻击、网络拥堵、系统不稳定、系统或设备故障、通讯故障、电力故障等公共服务因素或第三方服务瑕疵；\n" +
                "12.1.7\t其他基于法律法规或国家政策的规定。\n" +
                "尽管有前款约定，银米科技将采取合理行动积极促使服务恢复正常。\n" +
                "12.1.8\t用户未通过“水的快递”平台交易并接受商品或服务的。未通过 “水的快递”平台交易包括：用户未在“水的快递”平台与商户成立订单；用户虽在“水的快递”平台与商户成立订单，但未通过“水的快递”平台向商户支付费用。\n" +
                "12.2\t责任限制\n" +
                "“水的快递”平台可能同时为用户及用户的（交易）对手方提供服务，用户同意对XXXX可能存在的该等行为予以明确豁免任何实际或潜在的利益冲突，并不得以此来主张“水的快递”平台在提供服务时存在法律上的瑕疵。\n" +
                "十三、\t通知与送达\n" +
                "13.1\t有效联系方式\n" +
                "13.1.1\t用户在注册成为“水的快递”平台用户，并接受“水的快递”平台服务时，用户应该提供真实有效的联系方式（包括用户的联系电话、联系地址、电子邮件、传真等），对于联系方式发生变更的，用户有义务及时更新有关信息，并保持可被联系的状态。\n" +
                "13.1.2\t用户在注册“水的快递”平台用户时生成的用于登陆“水的快递”平台接收系统消息和即时信息的账号（包括子账号），也作为用户的有效联系方式。\n" +
                "13.1.3\t“水的快递”平台将通过用户的上述一种或多种联系方式向用户送达各类通知，该等通知的内容可能对用户的权利义务产生重大的有利或不利影响，请用户务必及时关注。\n" +
                "13.1.4\t用户有权通过用户注册时填写的手机号码或者电子邮箱获取用户感兴趣的商品广告信息、促销优惠等商业性信息；若用户不愿意接收此类信息，用户有权通过“水的快递”平台提供的相应退订功能进行退订。\n" +
                "13.2\t通知的送达\n" +
                "13.2.1\t“水的快递”平台通过上述联系方式向用户发出通知，其中以电子的方式发出的书面通知，包括但不限于在“水的快递”平台公告，向用户提供的联系电话发送手机短信，向用户提供的电子邮件发送电子邮件，向用户的账号发送信息、系统消息，在发送成功后即视为送达；以纸质载体发出的书面通知，按照提供联系地址交邮后的第五个自然日即视为送达。\n" +
                "13.2.2\t对于在“水的快递”平台上因交易活动引起的任何纠纷，用户同意司法机关（包括但不限于人民法院）可以通过手机短信、电子邮件或邮寄方式向用户送达法律文书（包括但不限于诉讼文书）。用户指定接收法律文书的手机号码、电子邮箱等联系方式为用户注册、更新“水的快递”平台时提供的联系方式，司法机关向上述联系方式发出法律文书即视为送达。用户指定的邮寄地址为用户的法定联系地址或用户提供的有效联系地址。\n" +
                "13.2.3\t用户同意司法机关可采取以上一种或多种送达方式向用户达法律文书，司法机关采取多种方式向用户送达法律文书，送达时间以上述送达方式中最先送达的为准。\n" +
                "13.2.4\t用户同意上述送达方式适用于各个司法程序阶段。如进入诉讼程序的，包括但不限于一审、二审、再审、执行以及督促程序等。\n" +
                "13.2.5\t你应当保证所提供的联系方式是准确、有效的，并进行实时更新。如果因提供的联系方式不准确或未及时告知变更后的联系方式，使法律文书无法送达或未及时送达，由此产生的法律后果由用户自行承担。\n" +
                "十四、\t法律适用、管辖与其他\n" +
                "14.1\t本协议之订立、生效、解释、修订、补充、终止、执行与争议解决均适用中华人民共和国大陆地区法律。\n" +
                "14.2\t如双方就本协议内容或其执行发生任何争议，双方应友好协商解决；协商不成时，任何一方均可向协议签订地人民法院提起诉讼。本协议签订地为中华人民共和国深圳市福田区。\n" +
                "14.3\t本协议中任一条款被视为废止、无效或不可执行，该条应视为可分的且并不影响本协议其余条款的有效性和可执行性。\n" +
                "14.4\t本协议未明示授权的其他权利仍由银米科技保留，用户在行使这些权利时须另外取得银米科技的书面许可。若银米科技未行使前述任何权利，并不构成对该权利的放弃。\n");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
