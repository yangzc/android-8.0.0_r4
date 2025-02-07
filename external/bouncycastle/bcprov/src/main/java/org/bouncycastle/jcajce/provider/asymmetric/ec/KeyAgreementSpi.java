package org.bouncycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import org.bouncycastle.asn1.x9.X9IntegerConverter;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
// BEGIN android-removed
// import org.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
// import org.bouncycastle.crypto.agreement.ECMQVBasicAgreement;
// import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
// import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
// END android-removed
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
// BEGIN android-removed
// import org.bouncycastle.crypto.params.MQVPrivateParameters;
// import org.bouncycastle.crypto.params.MQVPublicParameters;
// import org.bouncycastle.crypto.util.DigestFactory;
// END android-removed
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
// BEGIN android-removed
// import org.bouncycastle.jcajce.spec.MQVParameterSpec;
// END android-removed
import org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
// BEGIN android-removed
// import org.bouncycastle.jce.interfaces.MQVPrivateKey;
// import org.bouncycastle.jce.interfaces.MQVPublicKey;
// END android-removed

/**
 * Diffie-Hellman key agreement using elliptic curve keys, ala IEEE P1363
 * both the simple one, and the simple one with cofactors are supported.
 *
 * Also, MQV key agreement per SEC-1
 */
public class KeyAgreementSpi
    extends BaseAgreementSpi
{
    private static final X9IntegerConverter converter = new X9IntegerConverter();

    private String                 kaAlgorithm;

    private ECDomainParameters     parameters;
    private BasicAgreement         agreement;

    // BEGIN android-removed
    // private MQVParameterSpec       mqvParameters;
    // END android-removed
    private BigInteger             result;

    protected KeyAgreementSpi(
        String kaAlgorithm,
        BasicAgreement agreement,
        DerivationFunction kdf)
    {
        super(kaAlgorithm, kdf);

        this.kaAlgorithm = kaAlgorithm;
        this.agreement = agreement;
    }

    protected byte[] bigIntToBytes(
        BigInteger    r)
    {
        return converter.integerToBytes(r, converter.getByteLength(parameters.getCurve()));
    }

    protected Key engineDoPhase(
        Key     key,
        boolean lastPhase) 
        throws InvalidKeyException, IllegalStateException
    {
        if (parameters == null)
        {
            throw new IllegalStateException(kaAlgorithm + " not initialised.");
        }

        if (!lastPhase)
        {
            throw new IllegalStateException(kaAlgorithm + " can only be between two parties.");
        }

        CipherParameters pubKey;        
        // BEGIN android-removed
        // if (agreement instanceof ECMQVBasicAgreement)
        // {
        //     if (!(key instanceof MQVPublicKey))
        //     {
        //         ECPublicKeyParameters staticKey = (ECPublicKeyParameters)
        //             ECUtils.generatePublicKeyParameter((PublicKey)key);
        //         ECPublicKeyParameters ephemKey = (ECPublicKeyParameters)
        //             ECUtils.generatePublicKeyParameter(mqvParameters.getOtherPartyEphemeralKey());
        //
        //         pubKey = new MQVPublicParameters(staticKey, ephemKey);
        //     }
        //     else
        //     {
        //         MQVPublicKey mqvPubKey = (MQVPublicKey)key;
        //         ECPublicKeyParameters staticKey = (ECPublicKeyParameters)
        //             ECUtils.generatePublicKeyParameter(mqvPubKey.getStaticKey());
        //         ECPublicKeyParameters ephemKey = (ECPublicKeyParameters)
        //             ECUtils.generatePublicKeyParameter(mqvPubKey.getEphemeralKey());
        //
        //         pubKey = new MQVPublicParameters(staticKey, ephemKey);
        //     }
        // }
        // else
        // END android-removed
        {
            if (!(key instanceof PublicKey))
            {
                throw new InvalidKeyException(kaAlgorithm + " key agreement requires "
                    + getSimpleName(ECPublicKey.class) + " for doPhase");
            }

            pubKey = ECUtils.generatePublicKeyParameter((PublicKey)key);
        }

        try
        {
            result = agreement.calculateAgreement(pubKey);
        // BEGIN android-changed
        // Was:
        // } catch (final Exception e) {
        //     throw new InvalidKeyException("calculation failed: " + e.getMessage())
        //     {
        //         public Throwable getCause()
        //                     {
        //                         return e;
        //                     }
        //     };
        // }
        // END android-changed
        } catch (IllegalStateException e) {
            throw new InvalidKeyException("Invalid public key");
        }
        return null;
    }

    protected void engineInit(
        Key                     key,
        AlgorithmParameterSpec  params,
        SecureRandom            random) 
        throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        // BEGIN android-changed
        if (params != null && !(params instanceof UserKeyingMaterialSpec))
        // END android-changed
        {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }

        initFromKey(key, params);
    }

    protected void engineInit(
        Key             key,
        SecureRandom    random)
        throws InvalidKeyException
    {
        initFromKey(key, null);
    }

    private void initFromKey(Key key, AlgorithmParameterSpec parameterSpec)
        throws InvalidKeyException
    {
        // BEGIN android-removed
        // if (agreement instanceof ECMQVBasicAgreement)
        // {
        //     mqvParameters = null;
        //     if (!(key instanceof MQVPrivateKey) && !(parameterSpec instanceof MQVParameterSpec))
        //     {
        //         throw new InvalidKeyException(kaAlgorithm + " key agreement requires "
        //             + getSimpleName(MQVParameterSpec.class) + " for initialisation");
        //     }
        //
        //     ECPrivateKeyParameters staticPrivKey;
        //     ECPrivateKeyParameters ephemPrivKey;
        //     ECPublicKeyParameters ephemPubKey;
        //     if (key instanceof MQVPrivateKey)
        //     {
        //         MQVPrivateKey mqvPrivKey = (MQVPrivateKey)key;
        //         staticPrivKey = (ECPrivateKeyParameters)
        //             ECUtil.generatePrivateKeyParameter(mqvPrivKey.getStaticPrivateKey());
        //         ephemPrivKey = (ECPrivateKeyParameters)
        //             ECUtil.generatePrivateKeyParameter(mqvPrivKey.getEphemeralPrivateKey());
        //
        //         ephemPubKey = null;
        //         if (mqvPrivKey.getEphemeralPublicKey() != null)
        //         {
        //             ephemPubKey = (ECPublicKeyParameters)
        //                 ECUtils.generatePublicKeyParameter(mqvPrivKey.getEphemeralPublicKey());
        //         }
        //     }
        //     else
        //     {
        //         MQVParameterSpec mqvParameterSpec = (MQVParameterSpec)parameterSpec;
        //
        //         staticPrivKey = (ECPrivateKeyParameters)
        //             ECUtil.generatePrivateKeyParameter((PrivateKey)key);
        //         ephemPrivKey = (ECPrivateKeyParameters)
        //             ECUtil.generatePrivateKeyParameter(mqvParameterSpec.getEphemeralPrivateKey());
        //
        //         ephemPubKey = null;
        //         if (mqvParameterSpec.getEphemeralPublicKey() != null)
        //         {
        //             ephemPubKey = (ECPublicKeyParameters)
        //                 ECUtils.generatePublicKeyParameter(mqvParameterSpec.getEphemeralPublicKey());
        //         }
        //         mqvParameters = mqvParameterSpec;
        //         ukmParameters = mqvParameterSpec.getUserKeyingMaterial();
        //     }
        //
        //     MQVPrivateParameters localParams = new MQVPrivateParameters(staticPrivKey, ephemPrivKey, ephemPubKey);
        //     this.parameters = staticPrivKey.getParameters();
        //
        //     // TODO Validate that all the keys are using the same parameters?
        //
        //     agreement.init(localParams);
        // }
        // else
        // END android-removed
        {
            if (!(key instanceof PrivateKey))
            {
                throw new InvalidKeyException(kaAlgorithm + " key agreement requires "
                    + getSimpleName(ECPrivateKey.class) + " for initialisation");
            }

            ECPrivateKeyParameters privKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)key);
            this.parameters = privKey.getParameters();
            ukmParameters = (parameterSpec instanceof UserKeyingMaterialSpec) ? ((UserKeyingMaterialSpec)parameterSpec).getUserKeyingMaterial() : null;
            agreement.init(privKey);
        }
    }

    private static String getSimpleName(Class clazz)
    {
        String fullName = clazz.getName();

        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }


    protected byte[] calcSecret()
    {
        return bigIntToBytes(result);
    }

    public static class DH
        extends KeyAgreementSpi
    {
        public DH()
        {
            super("ECDH", new ECDHBasicAgreement(), null);
        }
    }

    // BEGIN android-removed
    // public static class DHC
    //     extends KeyAgreementSpi
    // {
    //     public DHC()
    //     {
    //         super("ECDHC", new ECDHCBasicAgreement(), null);
    //     }
    // }

    // public static class MQV
    //     extends KeyAgreementSpi
    // {
    //     public MQV()
    //     {
    //         super("ECMQV", new ECMQVBasicAgreement(), null);
    //     }
    // }

    // public static class DHwithSHA1KDF
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA1KDF()
    //     {
    //         super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    //     }
    // }

    // public static class DHwithSHA1KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA1KDFAndSharedInfo()
    //     {
    //         super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    //     }
    // }

    // public static class CDHwithSHA1KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public CDHwithSHA1KDFAndSharedInfo()
    //     {
    //         super("ECCDHwithSHA1KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    //     }
    // }

    // public static class DHwithSHA224KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA224KDFAndSharedInfo()
    //     {
    //         super("ECDHwithSHA224KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
    //     }
    // }

    // public static class CDHwithSHA224KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public CDHwithSHA224KDFAndSharedInfo()
    //     {
    //         super("ECCDHwithSHA224KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
    //     }
    // }

    // public static class DHwithSHA256KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA256KDFAndSharedInfo()
    //     {
    //         super("ECDHwithSHA256KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
    //     }
    // }

    // public static class CDHwithSHA256KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public CDHwithSHA256KDFAndSharedInfo()
    //     {
    //         super("ECCDHwithSHA256KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
    //     }
    // }

    // public static class DHwithSHA384KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA384KDFAndSharedInfo()
    //     {
    //         super("ECDHwithSHA384KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
    //     }
    // }

    // public static class CDHwithSHA384KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public CDHwithSHA384KDFAndSharedInfo()
    //     {
    //         super("ECCDHwithSHA384KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
    //     }
    // }

    // public static class DHwithSHA512KDFAndSharedInfo
    //      extends KeyAgreementSpi
    //  {
    //      public DHwithSHA512KDFAndSharedInfo()
    //      {
    //          super("ECDHwithSHA512KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
    //      }
    //  }

    //  public static class CDHwithSHA512KDFAndSharedInfo
    //      extends KeyAgreementSpi
    //  {
    //      public CDHwithSHA512KDFAndSharedInfo()
    //      {
    //          super("ECCDHwithSHA512KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
    //      }
    //  }

    // public static class MQVwithSHA1KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA1KDFAndSharedInfo()
    //     {
    //         super("ECMQVwithSHA1KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
    //     }
    // }

    // public static class MQVwithSHA224KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA224KDFAndSharedInfo()
    //     {
    //         super("ECMQVwithSHA224KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
    //     }
    // }

    // public static class MQVwithSHA256KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA256KDFAndSharedInfo()
    //     {
    //         super("ECMQVwithSHA256KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
    //     }
    // }

    // public static class MQVwithSHA384KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA384KDFAndSharedInfo()
    //     {
    //         super("ECMQVwithSHA384KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
    //     }
    // }

    // public static class MQVwithSHA512KDFAndSharedInfo
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA512KDFAndSharedInfo()
    //     {
    //         super("ECMQVwithSHA512KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
    //     }
    // }

    // public static class DHwithSHA1CKDF
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA1CKDF()
    //     {
    //         super("ECDHwithSHA1CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
    //     }
    // }

    // public static class DHwithSHA256CKDF
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA256CKDF()
    //     {
    //         super("ECDHwithSHA256CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
    //     }
    // }

    // public static class DHwithSHA384CKDF
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA384CKDF()
    //     {
    //         super("ECDHwithSHA384CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
    //     }
    // }

    // public static class DHwithSHA512CKDF
    //     extends KeyAgreementSpi
    // {
    //     public DHwithSHA512CKDF()
    //     {
    //         super("ECDHwithSHA512CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
    //     }
    // }

    // public static class MQVwithSHA1CKDF
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA1CKDF()
    //     {
    //         super("ECMQVwithSHA1CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
    //     }
    // }

    // public static class MQVwithSHA224CKDF
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA224CKDF()
    //     {
    //         super("ECMQVwithSHA224CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
    //     }
    // }

    // public static class MQVwithSHA256CKDF
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA256CKDF()
    //     {
    //         super("ECMQVwithSHA256CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
    //     }
    // }

    // public static class MQVwithSHA384CKDF
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA384CKDF()
    //     {
    //         super("ECMQVwithSHA384CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
    //     }
    // }

    // public static class MQVwithSHA512CKDF
    //     extends KeyAgreementSpi
    // {
    //     public MQVwithSHA512CKDF()
    //     {
    //         super("ECMQVwithSHA512CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
    //     }
    // }
    // END android-removed
}
