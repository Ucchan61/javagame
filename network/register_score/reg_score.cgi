#!/usr/bin/perl
use POSIX 'strftime';
use Fcntl ':flock';

# ���M���ꂽ�f�[�^���󂯎��
if ($ENV{'REQUEST_METHOD'} eq "POST") {
	read(STDIN, $buffer, $ENV{'CONTENT_LENGTH'});
}

# �f�[�^�𕪊�����
@pairs = split(/&/, $buffer);
# �e�f�[�^������ɕ�������%param�n�b�V���ɓ����
foreach $pair (@pairs) {
	($key, $value) = split(/=/, $pair);
	# value�����{��̏ꍇ�R�[�h������Ă���̂ŕϊ�����
	$value =~ s/%([a-fA-F0-9][a-fA-F-0-9])/pack("C", hex($1))/eg;
	$param{$key} = $value;
}

# �X�R�A���T�[�o�[��̃t�@�C���ɏ�������
unless (open(OUT, ">>score.txt")) {  # score.txt��ǉ��������݃��[�h�ŊJ��
	print "Sorry, I couldn't create score.txt\n";
} else {
	# ���ݎ������擾
	$now = strftime("%Y/%m/%d %H:%M", localtime);
	flock(OUT, LOCK_EX);  # �t�@�C�������b�N
	# ���ݎ����A���O�A�X�R�A����������
	print OUT "$now&$param{name}&$param{score}\n";
	flock(OUT, LOCK_UN);  # ���b�N������
}
close(OUT);  # �t�@�C�������

# �o�͂����HTML
print <<END;
Content-type: text/html

<html>
<head><title>���Ȃ��̓��_�͓o�^����܂���</title></head>
<body>
<h1>���Ȃ��̓��_�͓o�^����܂���</h1>
<p>$param{name}�����$param{score}�_�ł��B</p>
</body>
</html>
END
